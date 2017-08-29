package com.example.testApplication.ui;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.testApplication.entities.Address;
import com.example.testApplication.entities.Partner;
import com.example.testApplication.repositories.AddressRepository;
import com.example.testApplication.repositories.PartnerRepository;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
public class TestUI extends UI {
	private static final long serialVersionUID = 1L;
	
	private Address currentAdd = new Address();
	private Partner currentPartner = new Partner();

	private final PartnerRepository partnerRepo;
	private final AddressRepository addRepo;

	private FormLayout form;
	private Label label = new Label("Please fill out all fields");

	private Binder<Partner> partnerBinder;
	private Binder<Address> addressBinder;

	@Autowired
	public TestUI(PartnerRepository repository, AddressRepository addRepo) {
		this.partnerRepo = repository;
		this.addRepo = addRepo;
	}

	@Override
	protected void init(VaadinRequest request) {
		Button save = new Button("Save", VaadinIcons.INSERT);
		save.setEnabled(false);

		Button cancel = new Button("Cancel");

		form = buildForm(save);

		HorizontalLayout hLayout = new HorizontalLayout(save, cancel);

		VerticalLayout vLayout = new VerticalLayout(label, form, hLayout);
		setContent(vLayout);
		cancel.addClickListener(e -> {
			clearForm();
		});

		save.addClickListener(e -> {
			String name = currentPartner.getName();
			String phone = currentPartner.getPhone();
			String mail = currentPartner.getMail();

			String street = currentAdd.getStreet();
			String postcode = currentAdd.getPostcode();
			String city = currentAdd.getCity();

			Optional<Partner> partner = partnerRepo.findPersonComplete(phone, mail, name);
			Optional<Address> address = addRepo.findAddressComplete(street, postcode, city);

			// Check if partner already exists in Repo
			if (partner.isPresent()) {
				createErrorMessage(partner.get());
				return;
			}

			// Check if address already exists in Repo
			if (address.isPresent()) {
				// Take the address from Repo instead of form
				currentPartner.setAddress(address.get());
			} else {
				// Insert address to addRepo
				currentPartner.setAddress(currentAdd);
				addRepo.save(currentAdd);
			}

			currentPartner.setName(currentPartner.getName().trim());
			partnerRepo.save(currentPartner);
			label.setValue("Account successfull created");
			currentPartner = new Partner();
			currentAdd = new Address();
			addressBinder.setBean(currentAdd);
			partnerBinder.setBean(currentPartner);
		});
	}

	private void createErrorMessage(Partner partner) {
		label.setValue("Customer exists already");//

	}

	// Clear complete form after click on resetButton
	private void clearForm() {
		form.forEach(component -> ((TextField) component).clear());
	}

	// Create Form
	private FormLayout buildForm(Button btn) {
		FormLayout newForm = new FormLayout();

		TextField name = new TextField("Company name");
		name.setIcon(VaadinIcons.USER_CARD);
		name.setRequiredIndicatorVisible(true);
		name.setMaxLength(40);
		name.addValueChangeListener(e -> {
			btn.setEnabled(isValid());
			changeIcon(name);
		});
		newForm.addComponent(name);

		TextField phone = new TextField("Phone");
		phone.setIcon(VaadinIcons.PHONE);
		phone.setRequiredIndicatorVisible(true);
		phone.addValueChangeListener(e -> {
			btn.setEnabled(isValid());
			changeIcon(phone);
		});
		newForm.addComponent(phone);

		TextField mail = new TextField("Mail");
		mail.setIcon(VaadinIcons.MAILBOX);
		mail.setRequiredIndicatorVisible(true);
		mail.addValueChangeListener(e -> {
			btn.setEnabled(isValid());
			changeIcon(mail);
		});
		newForm.addComponent(mail);

		TextField streetAndNr = new TextField("Street and Nr");
		streetAndNr.setIcon(VaadinIcons.ROAD);
		streetAndNr.setRequiredIndicatorVisible(true);
		streetAndNr.addValueChangeListener(e -> {
			btn.setEnabled(isValid());
			changeIcon(streetAndNr);
		});
		newForm.addComponent(streetAndNr);

		TextField postcode = new TextField("Postcode");
		postcode.setRequiredIndicatorVisible(true);
		postcode.setMaxLength(8);
		postcode.addValueChangeListener(e -> {
			btn.setEnabled(isValid());
			changeIcon(postcode);
		});
		newForm.addComponent(postcode);

		TextField city = new TextField("City");
		city.setRequiredIndicatorVisible(true);
		city.addValueChangeListener(e -> {
			btn.setEnabled(isValid());
			changeIcon(city);
		});
		newForm.addComponent(city);

		partnerBinder = new Binder<>(Partner.class);
		addressBinder = new Binder<>(Address.class);

		partnerBinder.bind(name, Partner::getName, Partner::setName);

		partnerBinder.forField(phone).withValidator(str -> str.matches("[0-9]+"), "Only digits allowed")
				.bind(Partner::getPhone, Partner::setPhone);

		partnerBinder.forField(mail).withValidator(new EmailValidator("Enter valid mail address"))
				.bind(Partner::getMail, Partner::setMail);

		addressBinder.bind(streetAndNr, Address::getStreet, Address::setStreet);

		addressBinder.forField(postcode).withValidator(str -> str.matches("[0-9]+"), "Only digits allowed")
				.bind(Address::getPostcode, Address::setPostcode);

		addressBinder.forField(city).withValidator(str -> str.matches("[a-zA-Z]+"), "Only letters allowed")
				.bind(Address::getCity, Address::setCity);

		addressBinder.setBean(currentAdd);
		partnerBinder.setBean(currentPartner);
		return newForm;
	}

	private boolean isValid() {
		boolean result = false;
		for (Component component : form) {
			if (((TextField) component).isEmpty()) {
				result = false;
				break;
			}
			result = partnerBinder.isValid() && addressBinder.isValid();
		}
		return result;
	}

	private void changeIcon(TextField field) {
		field.setRequiredIndicatorVisible(field.isEmpty());
	}

}
