package com.courses.server.services.impl;

import com.courses.server.dto.request.InfoContactCourseRequest;
import com.courses.server.entity.*;
import com.courses.server.entity.Class;
import com.courses.server.exceptions.BadRequestException;
import com.courses.server.exceptions.NotFoundException;
import com.courses.server.repositories.*;
import com.courses.server.services.InfoContactCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InfoContactCourseServiceImpl implements InfoContactCourseService {
    @Autowired
    private InfoContactCourseRepository infoContactRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TraineeRepository traineeRepository;

    @Override
    public void create(InfoContactCourseRequest req) {
        if (req.getFullName() == null || req.getEmail() == null || req.getPhone() == null || req.getNote() == null
                || req.getClassId() == null) {
            throw new BadRequestException(400, "Please input full info");
        }

        InfoContactCourse infoContact = new InfoContactCourse(req.getFullName(), req.getEmail(), req.getPhone(),
                req.getNote(), req.getStatus());

        Class newClass = null;
        try {
            newClass = classRepository.findById(req.getClassId()).get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (newClass == null) {
            throw new NotFoundException(404, "New class  Không tồn tại");
        }

        infoContactRepository.save(infoContact);

        Order order = new Order();

        User user = null;
        try {
            user = userRepository.findByEmail(req.getEmail()).orElse(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (user != null) {
            order.setUser(user);
            List<Trainee> trainees = null;
            try {
                trainees = traineeRepository.findByUser(user);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            if (trainees.size() > 0) {
                for (Trainee trainee : trainees) {
                    if (trainee.getAClass().getId() == newClass.getId()) {
                        throw new BadRequestException(400, "Bạn đã đăng kí lớp này rồi");
                    }
                }
            }
        } else {
            Customer customer = customerRepository
                    .save(new Customer(req.getFullName(), req.getEmail(), req.getPhone()));
            order.setCustomer(customer);
        }

        order.setAClass(newClass);

        Coupon coupon = null;
        if (req.getCodeCoupon() != null) {
            try {
                coupon = couponRepository.findByCodeAccess(req.getCodeCoupon()).orElse(null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (coupon != null)
                order.setCoupon(coupon);
        }

        OrderPackage orderPackage = new OrderPackage();
        orderPackage.setOrder(order);
        orderPackage.set_package(newClass.getAPackage());

        order.setTotalCost(newClass.getAPackage().getSalePrice());
        orderPackage.setPackageCost(newClass.getAPackage().getSalePrice());
        if (coupon != null) {
            int countUseCoupon = 0;
            if (user != null) {
                countUseCoupon = orderRepository.getCountCouponUserOrder(coupon.getId(), user.getId()).size();
            } else {
                countUseCoupon = orderRepository
                        .getCountCouponCustomerOrder(coupon.getId(), order.getCustomer().getId()).size();
            }
            if (countUseCoupon < coupon.getQuantity()) {
                double discount = orderPackage.get_package().getSalePrice() * coupon.getDiscountRate() / 100;
                order.setTotalDiscount(discount);
                order.setTotalCost(order.getTotalCost() - discount);
            } else {
                throw new BadRequestException(400, "The discount code has expired");
            }
        } else {
            throw new BadRequestException(400, "The discount code is incorrect or has expired");
        }
        order.getOrderPackages().add(orderPackage);
        order.setStatus(1);
        orderRepository.save(order);
    }

    @Override
    public void update(Long id, InfoContactCourseRequest req) {
        InfoContactCourse infoContact = null;
        try {
            infoContact = infoContactRepository.findById(id).get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (infoContact == null) {
            throw new NotFoundException(404, "Info contact course  Không tồn tại");
        }
        if (req.getFullName() != null)
            infoContact.setFullName(req.getFullName());
        if (req.getEmail() != null)
            infoContact.setEmail(req.getEmail());
        if (req.getPhone() != null)
            infoContact.setPhone(req.getPhone());
        if (req.getNote() != null)
            infoContact.setNote(req.getNote());
        if (req.getStatus() != null) {
            infoContact.setStatus(req.getStatus());
        }

        infoContactRepository.save(infoContact);
    }

    @Override
    public void delete(Long id) {
        InfoContactCourse infoContact = null;
        try {
            infoContactRepository.findById(id).get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (infoContact == null) {
            throw new NotFoundException(404, "Info contact course  Không tồn tại");
        }
        infoContactRepository.delete(infoContact);
    }

    @Override
    public Page<InfoContactCourse> getList(Pageable paging) {
        return infoContactRepository.findAll(paging);
    }

    @Override
    public InfoContactCourse getDetail(Long id) {
        InfoContactCourse infoContact = null;
        try {
            infoContactRepository.findById(id).get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (infoContact == null) {
            throw new NotFoundException(404, "Info contact course  Không tồn tại");
        }
        return infoContact;
    }
}
