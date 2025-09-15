-- -----------------------------------------------------
-- Schema authentication
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `authentication-db`;

CREATE SCHEMA `authentication-db`;
USE `authentication-db`;

-- -----------------------------------------------------
-- Table `authentication-db`.`rol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `authentication-db`.`rol` (
  `id_rol` BIGINT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  `descripcion` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id_rol`)
  )
ENGINE=InnoDB
AUTO_INCREMENT = 1;


-- -----------------------------------------------------
-- Table `authentication-db`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `authentication-db`.`usuario` (
  `id_usuario` BIGINT NOT NULL AUTO_INCREMENT,
  `documento_identidad` VARCHAR(20) DEFAULT NULL,
  `nombre` VARCHAR(100) NOT NULL,
  `apellido` VARCHAR(100) NOT NULL,
  `fecha_nacimiento` DATE NOT NULL,
  `direccion` VARCHAR(255) NOT NULL,
  `telefono` VARCHAR(20) NULL DEFAULT NULL,
  `email` VARCHAR(255) NOT NULL UNIQUE,
  `salario_base` DECIMAL(10,0) NOT NULL,
  `id_rol` BIGINT NOT NULL,
  PRIMARY KEY (`id_usuario`),
  KEY `FK_id_rol` (`id_rol`),
  CONSTRAINT `FK_id_rol` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id_rol`)
  )
ENGINE=InnoDB
AUTO_INCREMENT = 1;


insert into rol values (1, "Cliente", "Rol solicitante de prestamo");
insert into rol values (2, "Administrador", "Rol administrador");
insert into rol values (3, "Asesor", "Rol asesor");

commit;
