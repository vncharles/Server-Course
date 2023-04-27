package com.courses.server.schedular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.courses.server.repositories.ClassRepository;
import com.courses.server.repositories.TraineeRepository;
import com.courses.server.repositories.UserPackageRepository;

@Component
public class ExpiredSchedular {

  @Autowired
	private ClassRepository classRepository;

  @Autowired
	private UserPackageRepository userPackageRepository;

  @Autowired
	private TraineeRepository traineeRepository;

  @Scheduled(cron = "0 15 18 * * ?", zone = "Europe/Paris")
  public void checkClassSchedular() {
    classRepository.checkStartPage();
  }

  @Scheduled(cron = "0 15 18 * * ?", zone = "Europe/Paris")
  public void checkTraineeSchedular() {
    userPackageRepository.checkEndPage();
    traineeRepository.checkEndPage();
    traineeRepository.checkEndPage();
  } 
}
