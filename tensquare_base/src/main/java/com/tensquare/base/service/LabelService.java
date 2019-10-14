package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    public List<Label> findAll() {
        return labelDao.findAll();
    }

    public Label findByID(String id) {
        return labelDao.findById(id).get();
    }

    public void save(Label label) {
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    public void update(Label label) {
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    public void deleteByID(String id) {
        labelDao.deleteById(id);
    }


    public List<Label> findSearch(Label label) {
        // 实现匿名内部类
        return labelDao.findAll(new Specification<Label>() {
            // new一个集合，并存放所有的条件
            List<Predicate> list = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                /**
                 * root,把条件封装到根对象中
                 * criteriaQuery，封装查询关键字
                 * criteriaBuilder，封装条件对象
                 */
                if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                    // 模糊查询 -- where labelname like 'xxx'
                    Predicate predicate = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }
                if (label.getState() != null && !"".equals(label.getState())) {
                    Predicate predicate = criteriaBuilder.like(root.get("state").as(String.class), label.getState());
                    list.add(predicate);
                }
                Predicate[] parr = new Predicate[list.size()];
                //  将list直接转成数组
                list.toArray(parr);
                return criteriaBuilder.and(parr);   // 类似 where labelname like 'xxx' and state like 'yyy'
            }
        });
    }

    public Page<Label> pageQuery(Label label, Integer page, Integer size) {
        // 封装分页对象
        Pageable pageable = PageRequest.of(page - 1, size);
        return labelDao.findAll(new Specification<Label>() {
            // new一个集合，并存放所有的条件
            List<Predicate> list = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                /**
                 * root,把条件封装到根对象中
                 * criteriaQuery，封装查询关键字
                 * criteriaBuilder，封装条件对象
                 */
                if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                    // 模糊查询 -- where labelname like 'xxx'
                    Predicate predicate = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }
                if (label.getState() != null && !"".equals(label.getState())) {
                    Predicate predicate = criteriaBuilder.like(root.get("state").as(String.class), label.getState());
                    list.add(predicate);
                }
                Predicate[] parr = new Predicate[list.size()];
                //  将list直接转成数组
                list.toArray(parr);
                return criteriaBuilder.and(parr);   // 类似 where labelname like 'xxx' and state like 'yyy'
            }
        }, pageable);
    }
}
