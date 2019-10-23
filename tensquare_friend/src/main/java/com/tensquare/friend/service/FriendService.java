package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    public int addFriend(String userid, String friendid) {
        Friend friend = friendDao.findByUseridAndFriendid(userid, friendid);
        // 判断userid到friendid方向是否存在数据,有就是重复添加,return 0
        if (friend != null) {
            return 0;
        }

        // 直接添加好友,让好友表中userid到friendid方向的type=0
        friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);

        // 判断friendid到userid方向是否有数据,如果有,把双方状态都改为1,1表示是否互相喜欢
        if (friendDao.findByUseridAndFriendid(friendid, userid) != null){
            // 把双发的islike改为1
            friendDao.updateIslike("1", userid, friendid);
            friendDao.updateIslike("1", friendid, userid);
        }
        return 1;
    }

    public int addNoFriend(String userid, String friendid) {
        // 判断是否已经是非好友
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userid,friendid);
        if (noFriend != null){
            return 0;
        }
        noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);

        return 1;
    }

    public void deleteFriend(String userid, String friendid) {
        // 删除好友表中userid到friendid的数据
        friendDao.deletefriend(userid,friendid);

        // 更新friendid到userid的islike=0
        friendDao.updateIslike("0", friendid, userid);

        // 费好友表中添加数据
        NoFriend noFriend = new NoFriend();
        noFriend.setFriendid(friendid);
        noFriend.setUserid(userid);
        noFriendDao.save(noFriend);
    }
}
