package com.alparslankilic.roomdb.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.alparslankilic.roomdb.model.Person;
import java.util.List;

@Dao
public interface PersonDao {

    @Query("Select * from PERSON ORDER BY ID")
    List<Person> loadAllPerson();

    @Insert
    void insertPerson(Person person);

    @Update
    void updatePerson(Person person);

    @Delete
    void delete(Person person);

    @Query("Select * from PERSON where id=:id")
    Person loadPersonById(int id);

}
