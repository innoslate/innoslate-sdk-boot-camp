package boot.innoslate.core;

import sdk.innoslate.db.Auth;
import sdk.innoslate.db.AuthFactory;
import sdk.innoslate.db.DatabaseService;
import sdk.innoslate.db.DatabaseServiceFactory;
import sdk.innoslate.db.models.InnoProject;
import sdk.innoslate.storage.ObjectStorageService;
import sdk.innoslate.storage.ObjectStorageServiceFactory;

public abstract class InnoslateRemote {
  public static final int PROJECT_ID = Integer.parseInt(System.getProperty("innoslateProjectId"));
  public static final String SLUG = System.getProperty("innoslateOrganizationSlug");
  public static DatabaseService ds;
  public static ObjectStorageService oss;

  static {
    try {
      Auth auth;
      if (System.getProperty("innoslateUserApiKey") != null) {
        auth = AuthFactory.withSecureAuthenticationKey(System.getProperty("innoslateSDKDomain"),
          System.getProperty("innoslateUserApiKey"), System.getProperty("innoslateCloudApiKey"),
          System.getProperty("innoslateUserApiSalt"), System.getProperty("innoslateCloudApiSalt"));
      } else {
        auth = AuthFactory.withUsernamePassword(System.getProperty("innoslateSDKDomain"),
          System.getProperty("username"), System.getProperty("password"), System.getProperty("innoslateCloudApiKey"));
      }
      ds = DatabaseServiceFactory.withOrganizationSlug(auth, SLUG);
      oss = ObjectStorageServiceFactory.withOrganizationSlug(auth, SLUG);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void createNewProject(String name, String description, String folder) {
    InnoProject project = ds.projects().create();
    project.setName(name);
    if (description == null) {
      description = "Generated from SDK";
    }
    project.setDescription(description);
    project.setFolder(folder);
    ds.projects().save(project);
  }

  public static void deleteProject(int projectId) {
    InnoProject project = ds.projects().get(projectId);
    if (project != null) {
      ds.projects().remove(project);
    }
  }
}
