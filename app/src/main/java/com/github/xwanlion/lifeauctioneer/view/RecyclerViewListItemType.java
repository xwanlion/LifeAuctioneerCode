package com.github.xwanlion.lifeauctioneer.view;

public class RecyclerViewListItemType {


        public static final int COMMON_ITEM_TYPE = 1;

        public static final int ADD_NEW_TYPE = 2;


        public enum Type {

            COMMON_ITEM(COMMON_ITEM_TYPE),

            ADD_NEW(ADD_NEW_TYPE);

            int code;


            Type(int code) {
                this.code = code;
            }


            public int getCode() {
                return code;
            }


            public static Type getItemTypeByCode(int code) {

                switch (code) {
                    case COMMON_ITEM_TYPE:
                        return Type.COMMON_ITEM;

                    case ADD_NEW_TYPE:
                        return Type.ADD_NEW;

                }

                return Type.COMMON_ITEM;

            }

        }

}
