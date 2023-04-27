package com.courses.server.service.impl;

import com.courses.server.dto.request.OrderRequest;
import com.courses.server.dto.request.OrderRequestAdmin;
import com.courses.server.dto.request.ProductOrderRequest;
import com.courses.server.entity.*;
import com.courses.server.entity.Class;
import com.courses.server.entity.Package;
import com.courses.server.repositories.*;
import com.courses.server.services.impl.EmailSenderService;
import com.courses.server.services.impl.OrderServiceImpl;
import com.courses.server.utils.AutoSetSuporter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PackageRepository packageRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private ComboRepository comboRepository;

    @Mock
    private OrderPackageRepository orderPackageRepository;

    @Mock
    private EmailSenderService senderService;

    @Mock
    private ClassRepository classRepository;

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private SettingRepository settingRepository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private OrderServiceImpl service;

    @Test
    public void addProductOrder() {
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
          new User("a", "p$q", Collections.emptyList()), null));

        MockedStatic<AutoSetSuporter> supporter = Mockito.mockStatic(AutoSetSuporter.class);
        supporter.when(() -> AutoSetSuporter.setSupporter(settingRepository, userRepository)).thenReturn(null);
        Mockito.when(packageRepository.findById(Mockito.any())).thenReturn(Optional.of(new Package()));
        Mockito.when(comboRepository.findById(Mockito.any())).thenReturn(Optional.of(new Combo() {{setComboPackages(Collections.emptySet());}}));
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(new com.courses.server.entity.User() {{setId(1L);}});

        ProductOrderRequest req = new ProductOrderRequest();
        req.setPackageId(1L);
        req.setComboId(1L);

        service.addProductOrder(req);

        Mockito.verify(orderRepository).save(Mockito.any());
        supporter.close();
    }

    @Test
    public void deleteProductOrders() {
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
          new User("a", "p$q", Collections.emptyList()), null));

        com.courses.server.entity.User user = new com.courses.server.entity.User() {{
            setId(1L);
        }};
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(user);
        Mockito.when(orderPackageRepository.findById(Mockito.any())).thenReturn(Optional.of(new OrderPackage() {{setOrder(new Order() {{setUser(user);}});}}));
        Mockito.when(orderPackageRepository.deleteProduct(Mockito.any())).thenReturn(1);

        service.deleteProductOrders(1L);

        Mockito.verify(orderRepository).save(Mockito.any());
    }

    @Test
    public void deleteProductOrders2() {
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
          new User("a", "p$q", Collections.emptyList()), null));

        com.courses.server.entity.User user = new com.courses.server.entity.User() {{
            setId(1L);
            setRole(new Setting() {{setSetting_value("ROLE_SUPPORTER");}});
        }};
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(user);
        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(new Order() {{setUser(user);}}));

        service.deleteProductOrders(1L, Boolean.TRUE);

        Mockito.verify(orderRepository).save(Mockito.any());
    }


//    @Test
//    public void createNoLogin() {
//        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
//        OrderRequest req = new OrderRequest("f", "e@e.e", "0987654321",
//          "1", Collections.singletonList(1L), Collections.singletonList(1L), 1L);
//        com.courses.server.entity.User user = new com.courses.server.entity.User() {{
//            setId(1L);
//            setRole(new Setting() {{setSetting_value("ROLE_SUPPORTER");}});
//        }};
//
//        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
//        Mockito.when(customerRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
//        Mockito.when(customerRepository.save(Mockito.any())).thenReturn(new Customer() {{setId(1L);}});
//        Mockito.when(couponRepository.findByCodeAccess(Mockito.anyString())).thenReturn(Optional.of(new Coupon() {{setQuantity(2);setId(1L);}}));
//        Mockito.when(orderRepository.getCountCouponCustomerOrder(Mockito.anyLong(), Mockito.anyLong())).thenReturn(List.of(new Order()));
//        Mockito.when(classRepository.findById(Mockito.any())).thenReturn(Optional.of(new Class() {{setAPackage(new Package() {{setSalePrice(1D);}});}}));
//        MockedStatic<AutoSetSuporter> supporter = Mockito.mockStatic(AutoSetSuporter.class);
//        supporter.when(() -> AutoSetSuporter.setSupporter(settingRepository, userRepository)).thenReturn(null);
//
//        service.createNoLogin(req);
//        Mockito.verify(orderRepository).save(Mockito.any());
//    }

    @Test
    public void createAdmin() {
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
          new User("a", "p$q", Collections.emptyList()), null));
        OrderRequestAdmin req = new OrderRequestAdmin();
        req.setEmail("e@e.e");
        req.setFullName("e");
        req.setStatus(2);
        req.setClassId(1L);
        req.setCodeCoupon("a");
        com.courses.server.entity.User user = new com.courses.server.entity.User() {{
            setId(1L);
            setRole(new Setting() {{setSetting_value("ROLE_SUPPORTER");}});
        }};

        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(user);
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(classRepository.findById(Mockito.any())).thenReturn(Optional.of(new Class() {{setAPackage(new Package() {{setSalePrice(1D);}});}}));
        Mockito.when(customerRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(customerRepository.save(Mockito.any())).thenReturn(new Customer() {{setId(1L);}});
        Mockito.when(orderPackageRepository.checkClassExistCustomer(Mockito.any(), Mockito.any())).thenReturn(Collections.emptyList());
        Mockito.when(couponRepository.findByCodeAccess(Mockito.anyString())).thenReturn(Optional.of(new Coupon() {{setQuantity(2);setId(1L);}}));

        service.createAdmin(req, null);
        Mockito.verify(orderRepository).save(Mockito.any());
    }

    @Test
    public void updateOrderAdmin() {
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
          new User("a", "p$q", Collections.emptyList()), null));
        OrderRequestAdmin req = new OrderRequestAdmin();
        req.setEmail("e@e.e");
        req.setFullName("e");
        req.setMobile("098754321");
        req.setStatus(2);
        req.setClassId(1L);
        req.setCodeCoupon("a");
        req.setNote("n");
        req.setClassId(1L);
        com.courses.server.entity.User user = new com.courses.server.entity.User() {{
            setId(1L);
            setRole(new Setting() {{setSetting_value("ROLE_SUPPORTER");}});
        }};

        Mockito.when(classRepository.findById(Mockito.any())).thenReturn(Optional.of(new Class() {{setAPackage(new Package() {{setSalePrice(1D);}});}}));
        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(new Order() {{setUser(user); setAClass(new Class() {{setId(1L);}});}}));
        Mockito.when(orderRepository.getCountCouponUserOrder(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Collections.emptyList());
        Mockito.when(orderPackageRepository.checkClassExistUser(Mockito.any(), Mockito.anyLong())).thenReturn(Collections.emptyList());
        Mockito.when(traineeRepository.checkClassExistTraniee(Mockito.any(), Mockito.anyLong())).thenReturn(Collections.emptyList());
        Mockito.when(couponRepository.findByCodeAccess(Mockito.anyString())).thenReturn(Optional.of(new Coupon() {{setQuantity(2);setId(1L);}}));

        service.UpdateOrderAdmin(req, null, 1L);
        Mockito.verify(orderRepository).save(Mockito.any());
    }


}