package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.provider.ConfigFile;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
/**
 *  事务回滚
 */
@Transactional(rollbackFor = Exception.class)
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }

    public void save(Spit spit) {
        spit.set_id(idWorker.nextId() + "");
        //发布日期   
        spit.setPublishtime(new Date());
        // 浏览量
        spit.setVisits(0);
        // 分享量
        spit.setShare(0);
        // 点赞数
        spit.setThumbup(0);
        // 回复数
        spit.setComment(0);
        // 状态   
        spit.setState("1");
        // 如果当前吐槽存在父节点,那么父节点的回复数需要+1 (给别人的评论下评论)
        if (spit.getParentid() != null && !"".equals(spit.getParentid())) {
            mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(spit.getParentid())),
                    new Update().inc("comment", 1), "spit");
        }

        spitDao.save(spit);
    }

    public void update(Spit spit) {
        spitDao.save(spit);
    }

    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

    public Page<Spit> findByParentid(String parentId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(parentId, pageable);
    }

    /**
     * 点赞操作
     *
     * @param spitid
     */
    public void thumbup(String spitid) {
//        // 方式一: 每次均访问数据库,效率低
//        Spit spit = spitDao.findById(spitid).get();
//        spit.setThumbup((spit.getThumbup() == null ? 0 : spit.getThumbup()) + 1);
//        spitDao.save(spit);

        // 方式二: 优化,使用原生MongoDB命令实现自增 --- db.spit.update({"_id":"1"}, {$inc:{thumbup:NumberInt(1}})
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is("1"));
        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");
    }
}
