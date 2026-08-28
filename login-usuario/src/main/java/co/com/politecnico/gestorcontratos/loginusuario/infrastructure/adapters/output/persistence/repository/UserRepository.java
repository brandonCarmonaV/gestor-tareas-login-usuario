package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.output.persistence.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {}
