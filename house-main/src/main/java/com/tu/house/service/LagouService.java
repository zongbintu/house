package com.tu.house.service;

import com.tu.house.model.dto.CompanyDto;
import java.util.List;

/**
 * @author Tu enum@foxmail.com.
 */
public interface LagouService {

  List<CompanyDto> companies(String cookie);
}
