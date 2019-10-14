package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {
    /**
     * 回答页面业务逻辑
     *
     * @param labelId  sql语句中的参数
     * @param pageable 分页显示
     * @return
     */
    @Query(value = "SELECT * FROM tb_pl, tb_problem WHERE id = problemid and labelid=? ORDER BY replytime DESC", nativeQuery = true)
    public Page<Problem> newList(String labelId, Pageable pageable);

    /**
     * 回答页面业务逻辑
     *
     * @param labelId  sql语句中的参数
     * @param pageable 分页显示
     * @return
     */
    @Query(value = "SELECT * FROM tb_pl, tb_problem WHERE id = problemid and labelid=? ORDER BY reply DESC", nativeQuery = true)
    public Page<Problem> hotList(String labelId, Pageable pageable);

    /**
     * 回答页面业务逻辑
     *
     * @param labelId  sql语句中的参数
     * @param pageable 分页显示
     * @return
     */
    @Query(value = "SELECT * FROM tb_pl, tb_problem WHERE id = problemid and labelid=? and reply=0 ORDER BY createtime DESC", nativeQuery = true)
    public Page<Problem> waitList(String labelId, Pageable pageable);

}
