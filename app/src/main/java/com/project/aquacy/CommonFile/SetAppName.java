package com.project.aquacy.CommonFile;

public class SetAppName {
    public final static String AppNameFCM = getFcmAppName();
    public static final String IMAGE_DIRECTORY_NAME =getImageDirectoryName();

    private static String getFcmAppName() {
        String retn = "";

            retn = WebUrlClass.AppNameFCM_EKATM;



        return retn;
    }

    private static String getImageDirectoryName() {
        String retn = "";

            retn = WebUrlClass.IMAGE_DIRECTORY_EKATM;

        return retn;
    }


  /*  public static void setToolbar(Toolbar toolbar, String AppNameToolbar) {

        if (AppNameToolbar.equalsIgnoreCase(WebUrlClass.app_name_toolbar_PM)) {
            toolbar.setLogo(R.mipmap.ic_simplify);
            toolbar.setTitle(WebUrlClass.app_name_toolbar_PM);
        } else if (AppNameToolbar.equalsIgnoreCase(WebUrlClass.app_name_toolbar_Vwb)) {
            toolbar.setLogo(R.mipmap.ic_vwb);
            toolbar.setTitle(WebUrlClass.app_name_toolbar_Vwb);
        } else if (AppNameToolbar.equalsIgnoreCase(WebUrlClass.app_name_toolbar_CRM)) {
            toolbar.setLogo(R.mipmap.ic_crm);
            toolbar.setTitle(WebUrlClass.app_name_toolbar_CRM);
        }

    }*/



}
