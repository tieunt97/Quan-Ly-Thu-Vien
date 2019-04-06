-- MySQL Script generated by MySQL Workbench
-- Wed Jan 10 15:33:10 2018
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema quanlythuvien_20153752
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema quanlythuvien_20153752
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `quanlythuvien_20153752` DEFAULT CHARACTER SET utf8 ;
USE `quanlythuvien_20153752` ;

-- -----------------------------------------------------
-- Table `quanlythuvien_20153752`.`docgia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quanlythuvien_20153752`.`docgia` (
  `idDocGia` CHAR(5) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `TenDG` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `NgaySinh` VARCHAR(15) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `GioiTinh` VARCHAR(15) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `Email` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `SoDT` VARCHAR(15) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `DiaChi` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  PRIMARY KEY (`idDocGia`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `quanlythuvien_20153752`.`nhanvien`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quanlythuvien_20153752`.`nhanvien` (
  `idNhanVien` CHAR(5) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `TenNV` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `NgaySinh` VARCHAR(15) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `GioiTinh` VARCHAR(15) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `DiaChi` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `SoDT` VARCHAR(15) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  PRIMARY KEY (`idNhanVien`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `quanlythuvien_20153752`.`muontra`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quanlythuvien_20153752`.`muontra` (
  `idMuonTra` CHAR(5) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `idDocGia` CHAR(5) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `idNhanVien` CHAR(5) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `NgayMuon` VARCHAR(15) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `NgayHenTra` VARCHAR(15) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `DatCoc` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`idMuonTra`),
  INDEX `keyNV` (`idNhanVien` ASC),
  INDEX `keyDG` (`idDocGia` ASC),
  CONSTRAINT `keyDG`
    FOREIGN KEY (`idDocGia`)
    REFERENCES `quanlythuvien_20153752`.`docgia` (`idDocGia`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `keyNV`
    FOREIGN KEY (`idNhanVien`)
    REFERENCES `quanlythuvien_20153752`.`nhanvien` (`idNhanVien`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `quanlythuvien_20153752`.`sach`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quanlythuvien_20153752`.`sach` (
  `idSach` CHAR(5) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `TenSach` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `NhaXB` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `TenTG` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `NamXB` VARCHAR(15) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `GiaSach` INT(11) NOT NULL,
  `TheLoai` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `NgonNgu` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `TrangThai` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`idSach`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `quanlythuvien_20153752`.`chitietmuontra`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quanlythuvien_20153752`.`chitietmuontra` (
  `idMuonTra` CHAR(5) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `idSach` CHAR(5) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `NgayTra` VARCHAR(15) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `TienPhat` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`idMuonTra`, `idSach`),
  INDEX `keySach` (`idSach` ASC),
  CONSTRAINT `keyMuonTra`
    FOREIGN KEY (`idMuonTra`)
    REFERENCES `quanlythuvien_20153752`.`muontra` (`idMuonTra`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `keySach`
    FOREIGN KEY (`idSach`)
    REFERENCES `quanlythuvien_20153752`.`sach` (`idSach`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `quanlythuvien_20153752`.`taikhoan`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quanlythuvien_20153752`.`taikhoan` (
  `idTaiKhoan` CHAR(5) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `passWord` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `loaiTK` VARCHAR(15) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  PRIMARY KEY (`idTaiKhoan`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

INSERT INTO `quanlythuvien_20153752`.`taikhoan` VALUE('admin', 'admin', 'admin');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
