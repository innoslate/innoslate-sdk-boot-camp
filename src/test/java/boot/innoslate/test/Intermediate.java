package boot.innoslate.test;

import org.junit.jupiter.api.Test;

import boot.innoslate.core.InnoslateRemote;
import sdk.innoslate.db.models.InnoClass;
import sdk.innoslate.db.models.InnoEntity;

import sdk.innoslate.db.models.InnoLabel;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Intermediate extends InnoslateRemote {
  /**
   * Adds a label to an entity
   */
  @Test
  public void addLabelToEntity() {
    final int entityId = 0;
    final String labelName = "";

    InnoEntity entity = ds.entities().get(entityId);
    InnoLabel label = ds.getSchema(PROJECT_ID).labels().getByName(labelName);

    if(label != null) {
      entity.labels().add(label);
      ds.entities().save(Collections.singletonList(entity));
    }
  }

  /**
   * Creates an innoslate entity
   */
  @Test
  public void createEntity() {
    List<InnoEntity> entityList = ds.entities().create(1);

    InnoEntity entity = entityList.get(0);

    final String clazz = "";
    final String name = "";
    final String number = "";
    entity.setInnoClass(clazz).setName(name).setNumber(number);

    ds.entities().save(Collections.singletonList(entity));
  }

  /**
   * Adds an relationship to two innoslate entities
   */
  @Test
  public void addRelationship() {
    final int actionId = 0;
    InnoEntity action = ds.entities().get(actionId);

    final int limit = 10;
    final int offset = 0;
    final String search = ""; // class:Asset
    List<? extends InnoEntity> entities = ds.entities().search(search, limit, offset, PROJECT_ID);

    if(entities.size() > 0 && action != null) {
      InnoEntity asset = entities.get(0);
      final String relationshipName = "performs";
      asset.relationships().addByName(relationshipName, action);

      ds.entities().save(new ArrayList<>(Arrays.asList(asset, action)));
    }
  }

  /**
   * Removes a relationship from two innoslate entities
   * Will throw error if both entities are not being saved at the same time.
   */
  @Test
  public void removeRelationship() {
    final int actionId = 0;
    InnoEntity action = ds.entities().get(actionId);

    final int limit = 10;
    final int offset = 0;
    final String search = ""; // class:Asset
    List<? extends InnoEntity> entities = ds.entities().search(search, limit, offset, PROJECT_ID);

    if(entities.size() > 0) {
      InnoEntity asset = entities.get(0);
      boolean success = asset.relationships().remove("RELATIONSHIP HERE", action);
      System.out.println("success: " + success);
      ds.entities().save(new ArrayList<>(Arrays.asList(asset, action)));
    }
  }

  /**
   * Shows how to query the schema and see all the classes
   */
  @Test
  public void querySchemaClasses() {
    for(InnoClass clazz : ds.getSchema(PROJECT_ID).classes().all()) {
      System.out.println("Class name: " + clazz.getName());
    }
  }

  /**
   * Will add an image to an entity
   */
  @Test
  public void addImageToEntity() throws IOException {
    final String pictureName = "";
    final String picturePath = "";

    InputStream is = Files.newInputStream(Paths.get(picturePath));
    String imageId = oss.create(PROJECT_ID, pictureName, is);

    List<InnoEntity> entityList = ds.entities().create(1);
    InnoEntity entity = entityList.get(0);

    final String clazz = "";
    final String name = "";
    entity.setInnoClass(clazz).setName(name);
    entity.setImageId(imageId);

    ds.entities().save(Collections.singletonList(entity));
  }

  /**
   * Will add a file to an artifact entity
   */
  @Test
  public void addFileToEntity() throws IOException {
    final String fileName = "";
    final String filePath = "";

    InputStream is = Files.newInputStream(Paths.get(filePath));
    String fileId = oss.create(PROJECT_ID, fileName, is);

    InnoEntity entity = ds.entities().create(1).get(0);
    final String clazz = "Artifact";
    final String name = "";

    entity.setInnoClass(clazz).setName(name);
    entity.attributes().set("File", fileId);

    ds.entities().save(Collections.singletonList(entity));
  }
}
