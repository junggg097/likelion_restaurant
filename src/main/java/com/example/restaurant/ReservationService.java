package com.example.restaurant;

import com.example.restaurant.dto.ReservationDto;
import com.example.restaurant.entity.Reservation;
import com.example.restaurant.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;

    /*
    // 0번 요구사항
    public ReservationDto create(Long restId, ReservationDto dto) {
        // 1. 식당 정보를 조회한다.
        Optional<Restaurant> optionalRestaurant
                = restaurantRepository.findById(restId);

        // 1-1. 만약 없는 식당이라면 사용자에게 에러를 반환한다.
        if (optionalRestaurant.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // 2. 새로운 예약 정보를 만들 준비를 한다.
        Reservation newEntity = new Reservation(
                dto.getDate(),
                dto.getReserveHour(),
                dto.getPeople(),
                dto.getDuration(),
                optionalRestaurant.get()
        );
        // 3. 저장한 결과를 바탕으로 응답을 반환해준다.
        return ReservationDto.fromEntity(reservationRepository.save(newEntity));
    }
    */
    /*
    // 1번 요구사항
    public ReservationDto create(Long restId, ReservationDto dto) {
        // 1. 식당 정보를 조회한다.
        Optional<Restaurant> optionalRestaurant
                = restaurantRepository.findById(restId);

        // 1-1. 만약 없는 식당이라면 사용자에게 에러를 반환한다.
        if (optionalRestaurant.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // 2. 식당이 여는 시간에 예약을 요청했는지 판단한다.
        Restaurant restaurant = optionalRestaurant.get();
        // 여는 시각 <= 요청 시각
        // restaurant.getOpenHours() <= dto.getReserveHour();
        if (restaurant.getOpenHours() > dto.getReserveHour())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        // 닫는 시각 > 요청 시각
        // restaurant.getCloseHours() > dto.getReserveHour();
        if (restaurant.getCloseHours() <= dto.getReserveHour())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        // 새로운 예약 정보를 만들 준비를 한다.
        Reservation newEntity = new Reservation(
                dto.getDate(),
                dto.getReserveHour(),
                dto.getPeople(),
                dto.getDuration(),
                optionalRestaurant.get()
        );
        // 저장한 결과를 바탕으로 응답을 반환해준다.
        return ReservationDto.fromEntity(reservationRepository.save(newEntity));
    }
    public List<ReservationDto> readAll(Long restId) {
        List<ReservationDto> reservationDtoList = new ArrayList<>();
        for (Reservation entity: reservationRepository.findAllByRestaurantId(restId)) {
            reservationDtoList.add(ReservationDto.fromEntity(entity));
        }
        return reservationDtoList;
    }

    */
    // 2번 요구사항
    public ReservationDto create(Long restId, ReservationDto dto) {
        // 식당 정보를 조회한다.
        Optional<Restaurant> optionalRestaurant
                = restaurantRepository.findById(restId);

        // 만약 없는 식당이라면 사용자에게 에러를 반환한다.
        if (optionalRestaurant.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // 1. 식당이 여는 시간에 예약을 요청했는지 판단한다.
        Restaurant restaurant = optionalRestaurant.get();
        // 여는 시각 <= 요청 시각
        // restaurant.getOpenHours() <= dto.getReserveHour();
        if (restaurant.getOpenHours() > dto.getReserveHour())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        // 닫는 시각 > 요청 시각
        // restaurant.getCloseHours() > dto.getReserveHour();
        if (restaurant.getCloseHours() <= dto.getReserveHour())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        // 2. 식당이 닫기전에 떠난다.
        // 요청 시각 + 머무는 시간 <= 닫는 시간
        // dto.getReserveHour() + dto.getDuration() <= restaurant.getCloseHours();
        if (dto.getReserveHour() + dto.getDuration() > restaurant.getCloseHours())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        // 새로운 예약 정보를 만들 준비를 한다.
        Reservation newEntity = new Reservation(
                dto.getDate(),
                dto.getReserveHour(),
                dto.getPeople(),
                dto.getDuration(),
                optionalRestaurant.get()
        );
        // 저장한 결과를 바탕으로 응답을 반환해준다.
        return ReservationDto.fromEntity(reservationRepository.save(newEntity));
    }


}

