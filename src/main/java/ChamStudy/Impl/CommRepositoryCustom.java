package ChamStudy.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ChamStudy.Dto.AdminMainCommDto;
import ChamStudy.Dto.CommSearchDto;
import ChamStudy.Entity.Comm_Board;

public interface CommRepositoryCustom  {
	Page<Comm_Board> getAdminComm(CommSearchDto commSearchDto, Pageable pageable);
	
	Page<AdminMainCommDto> getAdminMainCommDto(CommSearchDto commSearchDto,AdminMainCommDto adminMainCommDto ,Pageable pageable);
	
	Page<AdminMainCommDto> getAdminQnACommDto(CommSearchDto commSearchDto,AdminMainCommDto adminMainCommDto ,Pageable pageable);
	
	Page<AdminMainCommDto> getAdminMentoCommDto(CommSearchDto commSearchDto,AdminMainCommDto adminMainCommDto ,Pageable pageable);
}