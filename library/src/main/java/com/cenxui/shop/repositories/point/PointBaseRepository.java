package com.cenxui.shop.repositories.point;

import com.cenxui.shop.repositories.Repository;

public interface PointBaseRepository extends Repository {

    Point getAllUserPoint();

    Point getUserPoint(String mail);

    Point useUserPoint(String mail, Long value);

    Point addUserPoint(String mail, Long value);
}
