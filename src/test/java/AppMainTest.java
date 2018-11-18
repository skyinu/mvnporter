import com.skyinu.porter.Porter;
import com.skyinu.porter.PorterOptions;
import org.junit.Test;

/**
 * Created by chen on 2018/11/18.
 */
public class AppMainTest {
    @Test
    public void name() throws Exception {
        String repo = "https://repo1.maven.org/maven2";
        String library = "com.airbnb.android:airmapview:1.8.0";
        String repositoryId = "android";//
        String toRepository = "";//
        String save = "";
        System.out.println(repo);
        System.out.println(library);
        System.out.println(save);
        PorterOptions.Builder builder = new PorterOptions.Builder();
        builder.fromRepository(repo)
                .localDirectory(save)
                .dependency(library)
                .toRespository(toRepository)
                .toRespositoryId(repositoryId);
        Porter porter = new Porter(builder.build());
        porter.startDeploy();
        while (true){
            Thread.sleep(10000);
        }
    }
}
