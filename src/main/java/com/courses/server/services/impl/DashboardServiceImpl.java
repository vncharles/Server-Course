package com.courses.server.services.impl;

import com.courses.server.dto.response.DashboardAdminAndManager;
import com.courses.server.dto.response.DashboardChartBar;
import com.courses.server.dto.response.DashboardChartBarDto;
import com.courses.server.dto.response.DashboardDoughnut;
import com.courses.server.dto.response.PackageDTO;
import com.courses.server.exceptions.NotFoundException;
import com.courses.server.repositories.*;
import com.courses.server.services.DashboardService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired
    private UserPackageRepository userPackageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private OrderPackageRepository orderPackageRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public DashboardAdminAndManager getClasss() {
        DashboardAdminAndManager res = new DashboardAdminAndManager();
        res.setTotal_old_class(orderRepository.countOldClass());
        res.setTotal_new_class(orderRepository.countNewClass());
        return res;
    }

    @Override
    public DashboardAdminAndManager getUsers() {
        DashboardAdminAndManager res = new DashboardAdminAndManager();
        res.setTotal_new_user(userRepository.count_new_user());
        res.setTotal_old_user(userRepository.count_old_user());

        return res;
    }

    @Override
    public DashboardAdminAndManager getCombos() {
        DashboardAdminAndManager res = new DashboardAdminAndManager();
        res.setTotal_old_combo(orderPackageRepository.countOldCombo());
        res.setTotal_new_combo(orderPackageRepository.countNewCombo());

        return res;
    }

    @Override
    public DashboardAdminAndManager getPackages() {
        DashboardAdminAndManager res = new DashboardAdminAndManager();
        res.setTotal_old_package(orderPackageRepository.countOldPackage());
        res.setTotal_new_package(orderPackageRepository.countNewPackage());

        return res;
    }

    @Override
    public DashboardAdminAndManager getPosts() {
        DashboardAdminAndManager res = new DashboardAdminAndManager();
        res.setTotal_new_post(postRepository.countNewPost());
        res.setTotal_old_post(postRepository.countOldPost());

        return res;
    }

    @Override
    public DashboardAdminAndManager getTrainees() {
        DashboardAdminAndManager res = new DashboardAdminAndManager();
        res.setTotal_trainee_off(traineeRepository.count());
        res.setTotal_trainee_onl(userPackageRepository.count());

        return res;
    }

    @Override
    public DashboardAdminAndManager getOrders() {
        DashboardAdminAndManager res = new DashboardAdminAndManager();
        res.setTotal_class_month(orderRepository.countMonthClass());
        res.setTotal_combo_month(orderPackageRepository.countMonthCombo());
        res.setTotal_package_month(orderPackageRepository.countMonthPackage());

        return res;
    }

    @Override
    public DashboardDoughnut getDoughnut() {
        DashboardDoughnut res = new DashboardDoughnut();
        res.setTotal_verfi(orderPackageRepository.countOrderByStatus(2));
        res.setTotal_submit(orderPackageRepository.countOrderByStatus(1));
        res.setTotal_cancel(orderPackageRepository.countOrderByStatus(4));
        res.setTotal_done(orderPackageRepository.countOrderByStatus(3));
        return res;
    }

    public DashboardChartBar getDataDashnboard(List<DashboardChartBarDto> listmax, List<DashboardChartBarDto> listmin,
            String label, boolean flag) {
        DashboardChartBar res = new DashboardChartBar();
        List<String> labels = new ArrayList<String>();
        List<Double> maxs = new ArrayList<Double>();
        List<Double> mins = new ArrayList<Double>();
        for (int i = 0; i < listmax.size(); i++) {
            maxs.add(listmax.get(i).getValue());
            if (label.equals("Tuần")) {
                labels.add(label + " " + (i + 1));
            } else {
                labels.add(label + " " + listmax.get(i).getLabel());
            }
            DashboardChartBarDto barDtoMax = listmax.get(i);
            DashboardChartBarDto barDto = listmin.stream().filter(bar -> bar.getLabel().equals(barDtoMax.getLabel()))
                    .findFirst().orElse(null);
            mins.add(barDto == null ? null : barDto.getValue());
        }
        res.setList_label(labels);
        if (flag) {
            res.setList_revenue(maxs);
            res.setList_sales(mins);
        } else {
            res.setList_revenue(mins);
            res.setList_sales(maxs);
        }
        return res;
    }

    @Override
    public DashboardChartBar getChartBar(Integer categoty) {
        if (categoty == null || categoty < 1 || categoty > 3) {
            throw new NotFoundException(404, "Vui lòng chọn loại!");
        }
        if (categoty == 1) {
            List<DashboardChartBarDto> listRevenue = orderRepository.getListRevenueWeek();
            List<DashboardChartBarDto> listSales = orderRepository.getListSalesWeek();
            if (listRevenue.size() >= listSales.size()) {
                return getDataDashnboard(listRevenue, listSales, "Tuần", true);
            } else {
                return getDataDashnboard(listSales, listRevenue, "Tuần", false);
            }
        } else if (categoty == 2) {
            List<DashboardChartBarDto> listRevenue = orderRepository.getListRevenueMonth();
            List<DashboardChartBarDto> listSales = orderRepository.getListSalesMonth();
            if (listRevenue.size() >= listSales.size()) {
                return getDataDashnboard(listRevenue, listSales, "Tháng", true);
            } else {
                return getDataDashnboard(listSales, listRevenue, "Tháng", false);
            }
        } else {
            List<DashboardChartBarDto> listRevenue = orderRepository.getListRevenueYear();
            List<DashboardChartBarDto> listSales = orderRepository.getListSalesYear();
            if (listRevenue.size() >= listSales.size()) {
                return getDataDashnboard(listRevenue, listSales, "Năm", true);
            } else {
                return getDataDashnboard(listSales, listRevenue, "Năm", false);
            }
        }
    }
}
