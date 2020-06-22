package com.fdmgroup.hotelbookingsystem.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fdmgroup.hotelbookingsystem.model.Bookings;
import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.repository.BookingDao;

@Service
public class BookingService {

	@Autowired
	BookingDao bookingDao;

	@Autowired
	HotelService hotelService;

	public Bookings save(Bookings booking) {
		return bookingDao.save(booking);
	}


	public BigDecimal calculateTotalPrice(Bookings booking) {

		LocalDate checkInDate = booking.getCheckInDate();
		LocalDate checkOutDate = booking.getCheckOutDate();
		long stayDuration = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
		BigDecimal totalPrice;

			BigDecimal stayDurationBigDec = BigDecimal.valueOf(stayDuration);
			BigDecimal roomPrice = booking.getRoomPrice();
			BigDecimal extrasPrice = booking.getExtras().getPrice();
		if (stayDuration > 5) {
			totalPrice = (roomPrice.multiply(stayDurationBigDec).add(extrasPrice).subtract(roomPrice));
		} else {
			totalPrice = (roomPrice.multiply(stayDurationBigDec).add(extrasPrice));
		}

		return totalPrice;
	}

	public Optional<Bookings> retrieveOne(long bookingId) {
		return bookingDao.findByBookingId(bookingId);
	}


}
