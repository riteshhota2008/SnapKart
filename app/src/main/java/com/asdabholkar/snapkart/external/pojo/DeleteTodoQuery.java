package com.asdabholkar.snapkart.external.pojo;

import com.google.gson.annotations.SerializedName;

public class DeleteTodoQuery {

    @SerializedName("type")
    String type = "delete";

    @SerializedName("args")
    Args args;

    public DeleteTodoQuery(Integer todoId, Integer userId) {
        args = new Args();
        args.where = new Where();
        args.where.id = todoId;
        args.where.userId = userId;
    }

    class Args {

        @SerializedName("table")
        String table = "todo";

        @SerializedName("where")
        Where where;
    }

    class Where {
        @SerializedName("user_id")
        Integer userId;

        @SerializedName("id")
        Integer id;
    }
}
