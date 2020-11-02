package com.example.bruschatkaappspring.controller;

import com.example.bruschatkaappspring.model.*;
import com.example.bruschatkaappspring.model.entity.*;
import com.example.bruschatkaappspring.repository.*;


import com.example.bruschatkaappspring.response.PageResponse;
import com.example.bruschatkaappspring.response.Response;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@CrossOrigin("*")
@RestController
@Transactional
//@RequestMapping("/api")
public class UserController {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    StockRepository stockRepository;
    @Autowired
    ProductionRepository productionRepository;
    @Autowired
    PayTypeRepository payTypeRepository;
    @Autowired
    ArchiveRepository archiveRepository;
    @Autowired
    PaymentArchiveRepository paymentArchiveRepository;

    //CLIENT ADD
    @PostMapping("/addClient")
    public Response addClient(@RequestBody ClientReq clientReq) {
        Client client = new Client(clientReq.getName(), clientReq.getProductName(),
                clientReq.getAmount(), clientReq.getPrice(), clientReq.getPhoneNumber(),
                clientReq.getAddress(), clientReq.getOrderDate());

        if (clientReq.getPhoneNumber().length() == 13) {
            clientRepository.save(client);
            return new Response("Klient qo'shildi", true, null);
        } else {
            return new Response("Telefon raqam noto'g'ri terildi", false, null);
        }
    }

    //CLIENT EDIT
    @PutMapping("/editClient")
    public Response editClient(@RequestParam Integer id, @RequestBody ClientReq clientReq) {
        Client client = clientRepository.findById(id).get();
        client.setName(clientReq.getName());
        client.setProduct(clientReq.getProductName());
        client.setAmount(clientReq.getAmount());
        client.setPrice(clientReq.getPrice());
        client.setPhoneNumber(clientReq.getPhoneNumber());
        client.setAddress(clientReq.getAddress());

        if (clientReq.getPhoneNumber().length() == 13) {
            clientRepository.save(client);
            return new Response("Klient o'zgartirildi!", true, null);
        } else {
            return new Response("Telefon raqam noto'g'ri terildi!", false, null);
        }
    }

    //CLIENT DELETE
    @DeleteMapping("/deleteClient")
    public Response deleteClient(@RequestParam Integer id) {
        try {
            if(paymentRepository.findAmountByClient(clientRepository.findById(id).get())==clientRepository.findById(id).get().getPrice()*clientRepository.findById(id).get().getAmount() &&
            clientRepository.findById(id).get().getAmount()==deliveryRepository.deliveryAmount(id)) {
                Client client = clientRepository.findById(id).get();
                Archive archive = new Archive(
                        client.getName(),
                        client.getProduct(),
                        client.getAmount(),
                        client.getPrice(),client.getPhoneNumber(), client.getAddress(), client.getOrderDate());
                archiveRepository.save(archive);

                List<Payment> payment = paymentRepository.findAllByClientId(id);
                for (Payment p:payment) {
                    PaymentArchive paymentArchive = new PaymentArchive(
                            p.getAmount(), p.getPaymentDate(), p.getClient().getName(), p.getClient().getPhoneNumber(), p.getPayType()
                    );
                    paymentArchiveRepository.save(paymentArchive);
                }
                paymentRepository.deleteAllByClientId(id);
                clientRepository.deleteById(id);
                return new Response("O'chirildi", true, null);
            }
            else {
                return new Response("Klient bilan hisob kitobingiz bor!", false, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("O'chirilmadi", false, null);
        }
    }

    //CLIENT GET
    @GetMapping("/getClients")
    public HttpEntity<?> getAllClients(@RequestParam Integer page, @RequestParam String name) {
        Pageable pageable = PageRequest.of(page - 1, 5);
        Page<Client> filteredClients = clientRepository.findAllByNameContainingIgnoreCaseOrProductContainingIgnoreCaseOrPhoneNumberContainingIgnoreCase(name, name, name, pageable);

        List<ClientModel> filterList = new ArrayList<>();

        for (Client client : filteredClients) {
            ClientModel clientModel = new ClientModel(
                    client.getId(),
                    client.getName(),
                    client.getProduct(),
                    client.getAmount(),
                    client.getPrice(),
                    client.getPhoneNumber(),
                    client.getAddress(),
                    0,
                    -(client.getAmount() * client.getPrice() - paymentRepository.findAmountByClient(client)),
                    deliveryRepository.deliveryAmount(client.getId()),
                    client.getOrderDate()
            );
            filterList.add(clientModel);
        }
//        Collections.sort(filterList, Comparator.comparing(ClientModel::getDeliveredAmount).reversed());
//        Collections.sort(filterList, Comparator.comparing(ClientModel::getBalance));

        return ResponseEntity.ok(new PageResponse(filterList, filteredClients.getTotalElements(), filteredClients.getTotalPages()));
    }


    //CLIENT GET
    @GetMapping("/getClientsArchive")
    public HttpEntity<?> getClientsArchive(@RequestParam Integer page, @RequestParam String name) {
        Pageable pageable = PageRequest.of(page - 1, 5);
        Page<Archive> filteredClients = archiveRepository.findAllByNameContainingIgnoreCaseOrProductContainingIgnoreCaseOrPhoneNumberContainingIgnoreCase(name, name, name, pageable);

        List<ClientModel> filterList = new ArrayList<>();

        for (Archive archive : filteredClients) {
            ClientModel clientModel = new ClientModel(
                    archive.getId(),
                    archive.getName(),
                    archive.getProduct(),
                    archive.getAmount(),
                    archive.getPrice(),
                    archive.getPhoneNumber(),
                    archive.getAddress(),
                    0,
                    archive.getAmount()*archive.getPrice(),
                    archive.getAmount(),
                    archive.getOrderDate()
            );
            filterList.add(clientModel);
        }
//        Collections.sort(filterList, Comparator.comparing(ClientModel::getDeliveredAmount).reversed());
//        Collections.sort(filterList, Comparator.comparing(ClientModel::getBalance));

        return ResponseEntity.ok(new PageResponse(filterList, filteredClients.getTotalElements(), filteredClients.getTotalPages()));
    }



    //CLIENT MAKE PAYMENT
    @PostMapping("/makePayment")
    public Response makePayment(@RequestBody PaymentReq paymentReq) {
        if(clientRepository.findById(paymentReq.getClientId()).get().getAmount()*
                clientRepository.findById(paymentReq.getClientId()).get().getPrice()-
        paymentRepository.findAmountByClient(clientRepository.findById(paymentReq.getClientId()).get())>=paymentReq.getAmount()) {
            Payment payment = new Payment(paymentReq.getAmount(), paymentReq.getPaymentDate(),
                    clientRepository.findById(paymentReq.getClientId()).get(),
                    payTypeRepository.findById(paymentReq.getPayTypeId()).get());
            paymentRepository.save(payment);
            return new Response("To'lov muvoffaqiyatli amalga oshirildi!", true, null);
        }
        else {
            return new Response("To'lov summasi qarzdan katta bo'lishi mumkin emas!", false, null);
        }
    }

    @GetMapping("/getPayments")
    public List<?> getPayment(@RequestParam Integer id) {
        List<Payment> payments = paymentRepository.findAllByClientId(id);
        List<PaymentModel> filterList = new ArrayList<>();
        for (Payment payment : payments) {
            PaymentModel paymentModel = new PaymentModel(
                    payment.getAmount(),
                    paymentRepository.findAmountByClient(clientRepository.findById(id).get()),
                    payment.getPaymentDate(),
                    payment.getPayType().getName().name()
            );
            filterList.add(paymentModel);
        }
        return filterList;
    }

    @GetMapping("/getPaymentsArchive")
    public List<?> getPaymentA(@RequestParam Integer id, @RequestParam String phone) {
        List<PaymentArchive> payments = paymentArchiveRepository.findAllByClientPhone(phone);
        List<PaymentModel> filterList = new ArrayList<>();
        for (PaymentArchive payment : payments) {
            PaymentModel paymentModel = new PaymentModel(
                    payment.getAmount(),
                    0,
                    payment.getPaymentDate(),
                    payment.getPayType().getName().name()
            );
            filterList.add(paymentModel);
        }
        return filterList;
    }

    @GetMapping("/GetOverallAmount")
    public Integer getOverallAmount(@RequestParam Integer id) {
        return paymentRepository.findAmountByClient(clientRepository.findById(id).get());
    }
//
    @GetMapping("/GetOverallAmountA")
    public Integer getOverallAmountArchive(@RequestParam Integer id) {
        return paymentArchiveRepository.findAmountByArchive(archiveRepository.findById(id).get().getPhoneNumber());
    }

    @GetMapping("/GetTotalDebt")
    public Integer GetTotalDebt(@RequestParam Integer id) {
        return clientRepository.findById(id).get().getAmount() * clientRepository.findById(id).get().getPrice();
    }

    @GetMapping("/GetTotalDebtA")
    public Integer GetTotalDebtA(@RequestParam Integer id) {
        return archiveRepository.findById(id).get().getAmount() * archiveRepository.findById(id).get().getPrice();
    }

    //PRODUCT GET ALL
    @GetMapping("/getStock")
    public List<?> getAllProducts() {
        return stockRepository.findAll();
    }

    @PostMapping("/addProduct")
    public Response addProduct(@RequestBody ProductReq productReq) {
        try {
            Stock stock = stockRepository.findByName(productReq.getName());
            Production production = new Production(productReq.getName(), productReq.getAmount(), productReq.getProductionDate());

            stock.setAmount(stock.getAmount() + productReq.getAmount());

            stockRepository.save(stock);
            productionRepository.save(production);

            return new Response("Mahsulot qo'shildi", true, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("Xatolik", false, null);
        }
    }

    @GetMapping("/producedProducts")
    public List<?> producedProductsByDate(@RequestParam String date) {
//        LocalDate today = LocalDate.now();
//        LocalDate yesterday = today.minusDays(1);
        LocalDate parse = LocalDate.parse(date);
        return productionRepository.findAllByProductionDate(parse);
    }


    @GetMapping("/totalProducts")
    public Integer totalProductsAmount(@RequestParam String date) {
        LocalDate parse = LocalDate.parse(date);
        return productionRepository.totalAmount(parse);
    }

    @PostMapping("/addStock")
    public Response addStock(@RequestBody ProductReq productReq) {
        try {
            Stock stock = new Stock(productReq.getName(), productReq.getAmount());
            stockRepository.save(stock);
            return new Response("Yangi mahsulot qo'shildi", true, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("Xatolik", false, null);
        }
    }

    @DeleteMapping("/deleteStock")
    public Response deleteMapping(@RequestParam Integer id) {
        try {
            stockRepository.deleteById(id);
            return new Response("Mahsulot o'chirildi", true, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("Mahsulot o'chirilmadi", false, null);
        }
    }

    @PostMapping("/addDelivery")
    public Response addDelivery(@RequestBody DeliveryReq deliveryReq) {
        try {
            if((clientRepository.findById(deliveryReq.getId()).get().getAmount()-
                    deliveryRepository.deliveryAmount(deliveryReq.getId())>=
                    deliveryReq.getAmount()) && stockRepository.findByName(deliveryReq.getProductName()).getAmount()>=deliveryReq.getAmount()) {

                Delivery delivery = new Delivery(deliveryReq.getAmount(), deliveryReq.getDeliveryDate(),
                        deliveryReq.getId(), deliveryReq.getProductName());

                deliveryRepository.save(delivery);
                stockRepository.findByName(deliveryReq.getProductName()).setAmount(
                        stockRepository.findByName(deliveryReq.getProductName()).getAmount()-deliveryReq.getAmount()

                );
                return new Response("Mahsulot yetkazildi!", true, null);
            }
            else {
                return new Response("Xatolik!", true, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("Xatolik", false, null);
        }
    }

    @GetMapping("/deliveredProducts")
    public List<?> deliveredProducts(@RequestParam String date) {
        LocalDate parse = LocalDate.parse(date);
        List<Delivery> deliveries =  deliveryRepository.findAllByProductionDate(parse);
        List<DeliveryModel> list = new ArrayList<>();
        for(Delivery delivery:deliveries) {
            if(clientRepository.findById(delivery.getClientId()).isPresent()) {
                DeliveryModel deliveryModel = new DeliveryModel(
                        delivery.getId(),
                        delivery.getAmount(),
                        delivery.getDeliveryDate(),
                        (clientRepository.findById(delivery.getClientId()).get().getName()),
                        clientRepository.findById(delivery.getClientId()).get().getPhoneNumber(),
                        delivery.getProductName(),
                        clientRepository.findById(delivery.getClientId()).get().getAddress()
                );
                list.add(deliveryModel);
            }
            else if(archiveRepository.findById(delivery.getClientId()).isPresent()) {
                DeliveryModel deliveryModel = new DeliveryModel(
                        delivery.getId(),
                        delivery.getAmount(),
                        delivery.getDeliveryDate(),
                        (archiveRepository.findById(delivery.getClientId()).get().getName()),
                        archiveRepository.findById(delivery.getClientId()).get().getPhoneNumber(),
                        delivery.getProductName(),
                        archiveRepository.findById(delivery.getClientId()).get().getAddress()
                );
                list.add(deliveryModel);
            }
        }
        return list;
    }

}
