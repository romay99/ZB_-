package com.zerobase.Store_Table_Reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StoreTableReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreTableReservationApplication.class, args);
	}

}
