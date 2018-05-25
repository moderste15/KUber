package at.fhj.swd.k_uber.database;

import android.content.Context;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.models.nosql.RecipeDO;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.List;

public class RecipeProvider {
    private static final String LOGTAG = RecipeProvider.class.getSimpleName();
    private static RecipeProvider instance = null;
    private final Context context;
    private final AWSConfiguration awsConfiguration;

    private AmazonDynamoDBClient dbClient = null;
    private DynamoDBMapper dbMapper = null;


    public List<RecipeDO> getRecipes() {
        DynamoDBMapper mapper = getDynamoDBMapper();
        PaginatedList<RecipeDO> list = mapper.scan(RecipeDO.class, new DynamoDBScanExpression());
                return list;
    }


    public DynamoDBMapper getDynamoDBMapper() {
        if (dbMapper == null) {
            final AWSCredentialsProvider cp = getIdentityManager().getCredentialsProvider();
            dbClient = new AmazonDynamoDBClient(cp);
            dbMapper = DynamoDBMapper.builder()
                    .awsConfiguration(this.awsConfiguration)
                    .dynamoDBClient(dbClient)
                    .build();
        }
        return dbMapper;
    }

    public static RecipeProvider getInstance(Context context) {
        if (instance == null)
            instance = new RecipeProvider(context);
        return instance;
    }
    public IdentityManager getIdentityManager() {
        return IdentityManager.getDefaultIdentityManager();
    }

    private RecipeProvider(Context context) {
        this.context = context;
        this.awsConfiguration = new AWSConfiguration(context);

        IdentityManager identityManager = new IdentityManager(context, awsConfiguration);
        IdentityManager.setDefaultIdentityManager(identityManager);
    }

}
