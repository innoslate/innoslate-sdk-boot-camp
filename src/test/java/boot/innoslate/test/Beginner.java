package boot.innoslate.test;

import org.junit.jupiter.api.Test;

import boot.innoslate.core.InnoslateRemote;

import sdk.innoslate.db.data.Duration;
import sdk.innoslate.db.models.InnoEntity;

import java.util.Date;
import java.util.List;

public class Beginner extends InnoslateRemote {
  /**
   * Returns a single entity. Entity must already exist within database
   */
  @Test
  public void getSingleEntity() {
    final int entityId = 0; //Replace 0 with Entity ID
    InnoEntity entity = ds.entities().get(entityId);

    System.out.println("entities name's: " + entity.getName());
  }


  /**
   * Search the database for search string
   */
  @Test
  public void searchForEntities() {
    final int limit = 10;
    final int offset = 0;

    final String search = ""; //ie label:Activity OR Class:Action

    List<? extends InnoEntity> entities = ds.entities().search(search, limit, offset, PROJECT_ID);
    for(InnoEntity entity : entities) {
      System.out.println("Entity name: " + entity.getName());
    }
  }

  /**
   * Shows how to get access to basic attributes on an entity
   */
  @Test
  public void getEntityAttributes() {
    final int entityId = 0; //Replace 0 with Entity ID
    InnoEntity entity = ds.entities().get(entityId);

    String name = entity.getName();
    String number = entity.getNumber();
    String description = entity.getDescription();

    System.out.println("Entity name: " + name);
    System.out.println("Entity number: " + number);
    System.out.println("Entity description: " + description);

    if(entity.instanceOf("Action")) {
      Date startTime = (Date) entity.attributes().get("Start");
      Duration duration = (Duration) entity.attributes().get("Duration");

      if(startTime != null) {
        System.out.println("Entity startTime: " + startTime.toString());
      } else {
        System.out.println("Entity has no start");
      }
      if(duration != null) {
        System.out.println("Entity duration: " + duration.getValue() + " " + duration.getUnits());
      } else {
        System.out.println("Entity has no duration");
      }
    }
  }

  /**
   * Shows how to get a relationship and relationship attributes
   */
  @Test
  public void getEntityRelationships() {
    final int entityId = 0; //Replace 0 with Entity ID
    InnoEntity entity = ds.entities().get(entityId);

    //Gets all the entity's parents
    for(InnoEntity.EntityRelationship rel : entity.relationships().any("decomposes")) {
      InnoEntity parent = rel.getTarget();
      if(parent != null) {
        System.out.println("Entity's parent name: " + parent.getName());
      }
    }

    //Gets all the relationship attributes
    //Entity needs to be an Action
    for(InnoEntity.EntityRelationship rel : entity.relationships().any("consumes")) {
      InnoEntity resource = rel.getTarget();
      if(resource != null) {
        Object amount = rel.attributes().get("Amount");
        System.out.println(entity.getName() + " will consume this amount: " + amount.toString() + " of " + resource.getName());
      }
    }
  }
}
