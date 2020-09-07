package com.wata.payslip.service.Implements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wata.payslip.converter.TypeProjectConverter;
import com.wata.payslip.model.dtos.SearchData;
import com.wata.payslip.model.dtos.TypeProjectDTO;
import com.wata.payslip.model.entity.TypeProjectEntity;
import com.wata.payslip.repository.TypeProjectRepository;
import com.wata.payslip.service.Interface.ITypeProjectService;
import com.wata.payslip.utils.PagingUtil;
import com.wata.payslip.utils.SortUtil;

@Service
public class TypeProjectService implements ITypeProjectService {
	@Autowired
	private TypeProjectRepository typeProjectRepository;

	@Autowired
	private TypeProjectConverter typeProjectConverter;

	@Override
	public ResponseEntity<Map<String, Object>> createTypePreject(TypeProjectDTO typeProjectDTO) {
		TypeProjectEntity entity = typeProjectConverter.toEntity(typeProjectDTO);
		try {
			entity = typeProjectRepository.save(entity);
			Map<String, Object> response = new HashMap<>();
			response.put("typeProject", typeProjectConverter.toDTO(entity));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> getAllTypeProject() {
		try {
			List<TypeProjectDTO> dtos = typeProjectConverter.toDTOs(typeProjectRepository.findAll());
			Map<String, Object> response = new HashMap<>();
			response.put("typeProjects", dtos);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> getTypeProjectById(Integer id) {
		try {
			TypeProjectEntity entity = typeProjectRepository.findOneById(id);
			Map<String, Object> response = new HashMap<>();
			response.put("typeProjects", typeProjectConverter.toDTO(entity));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> deleteTypeProjectById(Integer id) {
		try {
			typeProjectRepository.deleteById(id);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> updateTypeProjectById(TypeProjectDTO typeProjectDTO, Integer id) {
		try {
			TypeProjectEntity entity = typeProjectRepository.findOneById(id);
			entity.setTypeName(typeProjectDTO.getTypeName());
			Map<String, Object> response = new HashMap<>();
			response.put("typeProject", typeProjectConverter.toDTO(typeProjectRepository.save(entity)));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> searchTypeByTypeName(SearchData searchData) {
		String typeName = searchData.getSearchValue();

		try {
			List<TypeProjectEntity> typeProjectEntities = new ArrayList<TypeProjectEntity>();
			Pageable paging = SortUtil.sortAndPaging(searchData);
			Page<TypeProjectEntity> pageTuts;
			pageTuts = (typeName == null) ? typeProjectRepository.findAll(paging)
					: typeProjectRepository.findByTypeNameContaining(typeName.trim(), paging);
			typeProjectEntities = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response = PagingUtil.getConvertResponse("TypeProject", typeProjectConverter.toDTOs(typeProjectEntities),
					pageTuts);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
