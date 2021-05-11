package com.tgrajkowski.model.zespol;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZespolRepository extends JpaRepository<Zespol, Long> {
}
