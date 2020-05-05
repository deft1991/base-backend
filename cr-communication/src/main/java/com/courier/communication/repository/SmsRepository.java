package com.courier.communication.repository;

import com.courier.communication.model.SmsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SmsRepository extends CrudRepository<SmsEntity, Long> {

    Optional<SmsEntity> findById(Long id);

    Optional<SmsEntity> findByExternalUserId(Long id);

}
