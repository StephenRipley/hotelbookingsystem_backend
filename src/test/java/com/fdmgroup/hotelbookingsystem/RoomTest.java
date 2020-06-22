package com.fdmgroup.hotelbookingsystem;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;

import com.fdmgroup.hotelbookingsystem.model.Room;
import com.fdmgroup.hotelbookingsystem.services.HotelService;
import com.fdmgroup.hotelbookingsystem.services.RoomService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.Assert.assertThat;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RoomTest {

	@Autowired
	RoomService roomService;

	@Autowired
	HotelService hotelService;

	Pageable pageable;

	private static Validator validator;

	Pageable firstPageWithTwoElements = PageRequest.of(0, 2);

	@BeforeEach
	public void createValidator() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
	public void test_ThatANewRoomCanBeAdded() {
		Room room = new Room();
		room.setRoomType("STANDARD");
		room.setPrice(new BigDecimal("70.00"));
		int numBeforeAdding = roomService.findAll(pageable).getSize();
		roomService.save(room);
		int numAfterAdding = roomService.findAll(pageable).getSize();
		assertNotEquals(numBeforeAdding, numAfterAdding);

	}

	@Test
	public void test_ThatAListOfRoomsCanBeRetrieved() {
		Page<Room> allRooms = roomService.findAll(firstPageWithTwoElements);
		int numOfRooms = allRooms.getSize();
		assert (numOfRooms > 0);
	}

	@Test
	public void test_FindByRoomType() {
		List<Room> allRooms = roomService.findByRoomType("STANDARD", firstPageWithTwoElements);
		int numOfRooms = allRooms.size();
		assert (numOfRooms > 0);
	}

	@Test
	public void test_ThatRoomsCanBefoundByExactPrice() {
		List<Room> allRooms = roomService.findByPrice(new BigDecimal("120"), firstPageWithTwoElements);
		int numOfRooms = allRooms.size();
		assert (numOfRooms > 0);
	}

	@Test
	public void test_RoomCanBeObtainedByTypeAndPrice() {
		Room knownRoom = roomService.findByRoomId(1L).get();
		Optional<Room> room = roomService.findByRoomTypeAndPrice(knownRoom.getRoomType(), knownRoom.getPrice());
		assertTrue(room.isPresent());
	}

}
