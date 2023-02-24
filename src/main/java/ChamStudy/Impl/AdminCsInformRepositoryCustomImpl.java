package ChamStudy.Impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ChamStudy.Dto.CsInformListDto;
import ChamStudy.Dto.QCsInformListDto;
import ChamStudy.Dto.UserSearchDto;
import ChamStudy.Entity.QCsInform;

public class AdminCsInformRepositoryCustomImpl implements AdminCsInformRepositoryCustom {

	private JPAQueryFactory queryFactory;
	
	public AdminCsInformRepositoryCustomImpl (EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	//제목으로 검색하기
	public BooleanExpression titleLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QCsInform.csInform.title.like("%" + searchQuery + "%");
				
	}
	
	@Override
	public Page<CsInformListDto> getInformList(UserSearchDto userSearchDto, CsInformListDto csInformListDto,
			Pageable pageable) {
		QCsInform csInform = QCsInform.csInform;
		
		List<CsInformListDto> content = queryFactory.select(
				new QCsInformListDto (
						csInform.id,
						csInform.title,
						csInform.viewCount,
						csInform.upDate)
				)
				.from(csInform)
				.where(titleLike(userSearchDto.getSearchQuery()))
				.orderBy(csInform.id.desc())
				.offset(pageable.getOffset())	//데이터를 가져올 시작 index
				.limit(pageable.getPageSize())	//한 번에 가져올 데이터의 최대 개수
				.fetch();
				
		long total = queryFactory.select(Wildcard.count)
				.from(csInform)
				.where(titleLike(userSearchDto.getSearchQuery()))
				.fetchOne();
		
		return new PageImpl<>(content, pageable, total);
		
	}
	
	

}
