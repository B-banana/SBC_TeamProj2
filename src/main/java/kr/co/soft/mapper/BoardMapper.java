package kr.co.soft.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import kr.co.soft.bean.BoardBean;
import kr.co.soft.bean.CareAnimal;
import kr.co.soft.bean.ChipBean;
import kr.co.soft.bean.DogBean;
import kr.co.soft.bean.LikeBoardBean;
import kr.co.soft.domain.BoardListDTO;

public interface BoardMapper {

	// seq 넘버 쓸 때는 selectkey 설정해 주어야 글을 찾을 수 있다. (자동으로 다음 글을 찾기 때문에)
	// @SelectKey(statement = "select dog_seq.nextval from dual", keyProperty =
	// "dog_num", before = true, resultType = int.class)
	// @SelectKey(statement = "select board_seq.nextval from dual", keyProperty =
	// "board_num", before = true, resultType = int.class)
	// @SelectKey(statement = "select (board_seq.nextval)-1 from dual", keyProperty
	// = "dog_num", before = true, resultType = int.class)

	// 칩 테이블 입력
	@Insert("insert into chip (chip_num, dog_name, phone) values (#{chip_num}, #{dog_name}, #{phone})")
	void addChipInfo(ChipBean chip);

	// 도그테이블 입력
	@Insert("insert into dog (dog_num, breed, color, sex, age, weight,neutering, charcteristic, address1, address2, dog_content, chip_num, dog_img, happen_date) "
			+ "values(dog_seq.nextval, #{breed}, #{color},#{sex},#{age},#{weight},#{neutering}, #{charcteristic,jdbcType=VARCHAR},#{address1},#{address2,jdbcType=VARCHAR},#{dog_content,jdbcType=VARCHAR},#{chip_num,jdbcType=VARCHAR},#{dog_img},#{happen_date})")
	boolean addDogInfo(DogBean dog);

	// 보드테이블 입력
	@Insert("insert into board(board_num, user_id, writer_phone, reg_time, modi_time,readcount, dog_num, board_category) "
			+ "values ((dog_seq.nextval)-1, #{user_id}, #{writer_phone},sysdate,sysdate,#{readcount},(dog_seq.nextval)-1,#{board_category})")
	boolean addBoardContent(BoardBean boardContent);

	// 칩 넘버 중복 체크
	@Select("select chip_num from chip where chip_num=#{chip_num}")
	String checkRFID(String chip_num);

	//메인 화면 게시글 3개 뽑아 오기
	@Select("select * from(select board.board_num, board.board_category,\r\n"
			+ "dog.breed, dog.color, dog.sex, dog.age, dog.weight, dog.neutering, dog.charcteristic,dog.happen_date,dog.address1,dog.dog_img \r\n"
			+ "from board, dog where board.board_num = dog.dog_num order by board.reg_time desc) top3\r\n"
			+ "where rownum<=3")
	List<BoardListDTO> getMainAnimalList();
	
	// 게시글 리스트 조회
	@Select("select * from dog, board where dog.dog_num=board.dog_num order by dog.dog_num desc") // chip num 까지 가져올 경우는
	List<BoardListDTO> getBoardList(RowBounds rowBounds);

	// 게시판 카테고리 별 리스트 뽑아오기
	@Select("select * from board, dog where board.board_num = dog.dog_num and board.board_category like #{board_category} order by dog.dog_num desc")
	List<BoardListDTO> getCategoryList(String category, RowBounds rowBounds);

	// 조회수 업데이트
	@Update("update board set readcount = readcount+1 where board_num=#{board_num}")
	void updateReadcount(int board_num);

	// 게시글 전체 수 조회
	@Select("select count(*) from board")
	int getContentCnt();

	// 카테고리 별 게시글 수 조회
	@Select("select count(*) from board where board_category=#{board_category}")
	int getCategoryCnt(String category);

	// 게시글 하나 갖고 오기
	@Select("select * from board left outer join dog on dog.dog_num = board.dog_num left outer join chip on dog.chip_num = chip.chip_num where dog.dog_num=#{dog_num}")
	BoardListDTO getContentInfo(int board_no);

	// 게시물 삭제
	@Delete("delete from dog where dog_num=#{dog_num}")
	int deleteBoardContent(int board_no);

	// 게시글 삭제(chip으로)
	@Delete("delete from chip where chip_num=#{chip_num}")
	int deleteChip(String chip_num);

	// 칩 번호 수정
	@Update("update chip set chip_num=#{chip_num}, dog_name=#{dog_name}, phone=#{phone} where chip_num=#{chip_num}")
	boolean modifyChipInfo(ChipBean chip);

//	@Update("update dog set dog_num=#{dog_num}, breed=#{breed}, color=#{color},sex=#{sex}, age=#{age}, weight=#{weight}, neutering=#{neutering}, charcteristic=#{charcteristic,jdbcType=VARCHAR},address1=#{address1},address2=#{address2,jdbcType=VARCHAR}, dog_content=#{dog_content,jdbcType=VARCHAR}, chip_num=#{chip_num,jdbcType=VARCHAR},dog_img=#{dog_img}, happen_date=#{happen_date} where dog_num=#{dog_num}")
//	boolean modifyDogInfo(DogBean dog);
//
//	@Update("update board set board_num=#{board_num}, user_id=#{user_id}, writer_phone=#{writer_phone}, reg_time=#{reg_time}, modi_time=sysdate,readcount=#{readcount},dog_num=#{dog_num,jdbcType=VARCHAR}, board_category=#{board_category} where board_num=#{board_num}")
//	boolean modifyBoardContent(BoardBean boardContent);

	@Update("update dog set dog_num=#{dog_num}, breed=#{breed}, color=#{color},sex=#{sex}, age=#{age}, weight=#{weight}, neutering=#{neutering}, charcteristic=#{charcteristic,jdbcType=VARCHAR},address1=#{address1},address2=#{address2,jdbcType=VARCHAR}, dog_content=#{dog_content,jdbcType=VARCHAR}, chip_num=#{chip_num,jdbcType=VARCHAR},dog_img=#{dog_img}, happen_date=#{happen_date} where dog_num=#{dog_num}")
	boolean modifyDogInfo(BoardListDTO m);

	@Update("update board set board_num=#{board_num}, user_id=#{user_id}, writer_phone=#{writer_phone}, reg_time=#{reg_time}, modi_time=sysdate,readcount=#{readcount},dog_num=#{dog_num,jdbcType=VARCHAR}, board_category=#{board_category} where board_num=#{board_num}")
	boolean modifyBoardContent(BoardListDTO m);

	// 관심글 추가
	@Insert("insert into likes(user_id, board_num, add_date) values(#{user_id},#{board_num},sysdate)")
	boolean addLikeContent(LikeBoardBean likeBean);

	// 관심글 삭제
	@Delete("delete from likes where user_id =#{user_id} and board_num =#{board_num}")
	boolean deleteLikeContent(LikeBoardBean likeBean);

	// 관심글 리스트에 좋아요 글 뽑아오기 위한 쿼리문
	@Select("select * from likes where user_id=#{user_id}")
	List<LikeBoardBean> getLikeBoardList(String user_id);

	//나의 관심글 리스트
	@Select("select * from dog, board, likes where dog.dog_num =board.board_num and likes.board_num = board.board_num and likes.user_id =#{user_id}")
	List<BoardListDTO> getMyBoardList(String user_id);

	// 좋아요 체크
//	@Select("select * from likes where user_id=#{user_id}")
//	List<LikeBoardBean> getLikeBoardNumber(String user_id);

	// 관심 동물 추가
	@Insert("insert into likeAnimalList(user_id, animal_code, add_date) values(#{user_id},#{animal_code}, sysdate)")
	boolean addLikeAnimal(CareAnimal animal);

	// 관심 동물 삭제
	@Delete("delete from likeAnimalList where user_id =#{user_id} and animal_code =#{animal_code}")
	boolean deleteAnimal(CareAnimal c);

	// 관심동물 리스트
	@Select("select * from likeAnimalList where user_id=#{user_id} order by add_date desc")
	List<CareAnimal> getAnimalList(String user_id);
	
	//내가 쓴 글 
	@Select("select * from board, dog where board.board_num = dog.dog_num and board.user_id=#{user_id}")
	List<BoardListDTO> getMyWrite(String user_id);

//	@Update("update chip set chip_num=#{chip_num}, dog_name=#{dog_name}, phone=#{phone} where chip_num=#{chip_num}")  
//	boolean modifyChipInfo(BoardListDTO modifyBean);
//
//	@Update("update dog set dog_num=#{dog_num}, breed=#{breed}, color=#{color},sex=#{sex}, age=#{age}, weight=#{weight}, neutering=#{neutering}, charcteristic=#{charcteristic,jdbcType=VARCHAR},address1=#{address1},address2=#{address2,jdbcType=VARCHAR}, dog_content=#{dog_content,jdbcType=VARCHAR}, chip_num=#{chip_num,jdbcType=VARCHAR},dog_img=#{dog_img}, happen_date=#{happen_date} where dog_num=#{dog_num}")
//	boolean modifyDogInfo(BoardListDTO modifyBean);
//
//	@Update("update board set board_num=#{board_num}, user_id=#{user_id}, writer_phone=#{writer_phone}, reg_time=#{reg_time}, modi_time=sysdate, likecount=#{likecount},readcount=#{readcount},dog_num=#{dog_num,jdbcType=VARCHAR}, board_category=#{board_category} where board_num=#{board_num}")
//	boolean modifyBoardContent(BoardListDTO modifyBean);

}
