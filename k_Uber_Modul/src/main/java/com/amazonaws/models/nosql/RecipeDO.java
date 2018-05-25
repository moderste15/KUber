package com.amazonaws.models.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "kuber-mobilehub-1659047369-recipe")

public class RecipeDO {
    private String _recipeId;
    private String _fotoUrl;
    private Map<String, String> _ingredients;
    private String _name;
    private String _text;
    private List<String> _type;

    @DynamoDBHashKey(attributeName = "recipeId")
    @DynamoDBAttribute(attributeName = "recipeId")
    public String getRecipeId() {
        return _recipeId;
    }

    public void setRecipeId(final String _recipeId) {
        this._recipeId = _recipeId;
    }


    @DynamoDBAttribute(attributeName = "fotoUrl")
    public String getFotoUrl() {
        return _fotoUrl;
    }

    public void setFotoUrl(final String _fotoUrl) {
        this._fotoUrl = _fotoUrl;
    }


    @DynamoDBAttribute(attributeName = "ingredients")
    public Map<String, String> getIngredients() {
        return _ingredients;
    }

    public void setIngredients(final Map<String, String> _ingredients) {
        this._ingredients = _ingredients;
    }


    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return _name;
    }

    public void setName(final String _name) {
        this._name = _name;
    }


    @DynamoDBAttribute(attributeName = "text")
    public String getText() {
        return _text;
    }

    public void setText(final String _text) {
        this._text = _text;
    }


    @DynamoDBAttribute(attributeName = "type")
    public List<String> getType() {
        return _type;
    }

    public void setType(final List<String> _type) {
        this._type = _type;
    }

}
