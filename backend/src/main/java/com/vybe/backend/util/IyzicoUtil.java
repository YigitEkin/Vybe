package com.vybe.backend.util;

import com.iyzipay.Options;
import com.iyzipay.model.*;
import com.iyzipay.request.CreateCheckoutFormInitializeRequest;
import com.iyzipay.request.CreatePaymentRequest;
import com.vybe.backend.model.dto.IncomingTransactionDTO;
import com.vybe.backend.model.dto.TransactionDTO;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class IyzicoUtil {
    @Value("${iyzico.apiKey}")
    private String apiKey;
    @Value("${iyzico.privateKey}")
    private String privateKey;
    @Value("${iyzico.baseUrl}")
    private String baseUrl;
    private Options options;
    @PostConstruct
    public void init() {
        options = new Options();
        options.setApiKey(apiKey);
        options.setSecretKey(privateKey);
        options.setBaseUrl(baseUrl);
    }


    public String executeTransaction(IncomingTransactionDTO transactionDTO) {

        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setLocale(Locale.TR.getValue());
        double amount = transactionDTO.getReceivedCoins() * 0.05;
        request.setConversationId("0");
        request.setPrice(BigDecimal.valueOf(amount * 0.96 - 0.25)); // Actual cost of items
        request.setPaidPrice(BigDecimal.valueOf(amount)); // 0.25 Kuruş + %4 iyzico commission
        request.setCurrency(Currency.TRY.name()); // Currency is TRY for now
        request.setInstallment(1); // We can add installment options later
        request.setBasketId("0"); // Default
        request.setPaymentChannel(PaymentChannel.WEB.name());
        request.setPaymentGroup(PaymentGroup.PRODUCT.name());

        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardHolderName(transactionDTO.getName() + transactionDTO.getSurname());
        paymentCard.setCardNumber(transactionDTO.getCardNumber());
        paymentCard.setExpireMonth(transactionDTO.getExpirationMonth());
        paymentCard.setExpireYear(transactionDTO.getExpirationYear());
        paymentCard.setCvc(transactionDTO.getCvc());
        paymentCard.setRegisterCard(0); // Default
        request.setPaymentCard(paymentCard);

        Buyer buyer = new Buyer();
        buyer.setId("0"); // Helps monitor user cash flow in Iyzico Control Panel, check out
        buyer.setName(transactionDTO.getName());
        buyer.setSurname(transactionDTO.getSurname());
        buyer.setGsmNumber("+905350000000"); // Iyzico requires this field
        buyer.setEmail("email@email.com"); // Iyzico requires this field
        buyer.setIdentityNumber("74300864791"); // Iyzico requires this field
        buyer.setLastLoginDate("2015-10-05 12:43:35"); // Iyzico requires this field
        buyer.setRegistrationDate("2013-04-21 15:12:09"); // Iyzico requires this field
        buyer.setRegistrationAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1"); // Iyzico requires this field
        buyer.setIp("4.4.8.8"); // Iyzico requires this field
        buyer.setCity("Istanbul"); // Iyzico requires this field
        buyer.setCountry("Turkey"); // Iyzico requires this field
        buyer.setZipCode("34732"); // Iyzico requires this field
        request.setBuyer(buyer);

        Address shippingAddress = new Address();
        shippingAddress.setContactName(transactionDTO.getName() + transactionDTO.getSurname()); // Iyzico requires this field
        shippingAddress.setCity("Istanbul"); // Iyzico requires this field
        shippingAddress.setCountry("Turkey"); // Iyzico requires this field
        shippingAddress.setAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1"); // Iyzico requires this field
        shippingAddress.setZipCode("34742"); // Iyzico requires this field
        request.setShippingAddress(shippingAddress);

        Address billingAddress = new Address();
        billingAddress.setContactName(transactionDTO.getName() + transactionDTO.getSurname()); // Iyzico requires this field
        billingAddress.setCity("Istanbul"); // Iyzico requires this field
        billingAddress.setCountry("Turkey"); // Iyzico requires this field
        billingAddress.setAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1"); // Iyzico requires this field
        billingAddress.setZipCode("34742"); // Iyzico requires this field
        request.setBillingAddress(billingAddress);

        List<BasketItem> basketItems = new ArrayList<>();

        BasketItem basketItem = new BasketItem();
        basketItem.setId("B1");
        basketItem.setName("Vybe Coin");
        basketItem.setCategory1("Vybe Coin");
        basketItem.setCategory2("Coin");
        basketItem.setItemType(BasketItemType.VIRTUAL.name());
        basketItem.setPrice(BigDecimal.valueOf(amount * 0.96 - 0.25));
        basketItems.add(basketItem);
        request.setBasketItems(basketItems);

        Payment payment = Payment.create(request, options);
        return payment.getErrorGroup();

    }
}