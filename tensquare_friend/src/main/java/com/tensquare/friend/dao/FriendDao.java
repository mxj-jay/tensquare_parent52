package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend, String> {
    Friend findByUseridAndFriendid(String userid, String friendid);

    @Modifying
    @Query(value = "UPDATE tb_friend SET islike=? WHERE userid=? and friendid=?", nativeQuery = true)
    void updateIslike(String islike, String userid, String friendid);

    @Modifying
    @Query(value = "delete from tb_friend WHERE userid=? and friendid=?", nativeQuery = true)
    void deletefriend(String userid, String friendid);
}