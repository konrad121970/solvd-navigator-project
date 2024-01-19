package com.solvd.laba.persistence;

import com.solvd.laba.model.Road;
import java.util.List;

public interface RoadRepository {

    void create(Road road);

    Road findById(int id);

    List<Road> findByCityId(int cityId);

    void update(Road road);

    void deleteById(int id);
}
