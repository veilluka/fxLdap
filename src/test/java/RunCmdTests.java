import backend.CmdLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RunCmdTests {




    public static void testA() throws Exception {


        String[] args = new String[6];
        args[0] = "-op";
        args[1] = "delete_collection";
        args[2] = "-env";
        args[3] = "DIRX_8.7_PROD_PROV";
        args[4] = "-projectFile";
        args[5] = "O:\\Anwendungen\\Application DirX\\70 Projekte\\06 Migration 8.7\\DATEN_TRANSFER\\" +
                "TransferProd2Prod\\Prod2ProdData\\Provisioning\\BO.project.xlsx";

        CmdLine cmdLine = new CmdLine();
        cmdLine.runCmd(args);


    }

    public static void testB() throws Exception {


        String[] args = new String[6];
        args[0] = "-op";
        args[1] = "export_collection";
        args[2] = "-env";
        args[3] = "E_PROD_WRITE";
        args[4] = "-projectDir";
        args[5] = "O:\\Anwendungen\\Application DirX\\70 Projekte\\06 Migration 8.7\\DATEN_TRANSFER\\Prod2Prod\\OID";


        CmdLine cmdLine = new CmdLine();
        cmdLine.runCmd(args);


    }

    public static void testC() throws Exception {


        String[] args = new String[6];
        args[0] = "-op";
        args[1] = "export_collection";
        args[2] = "-env";
        args[3] = "DirXProdADWriteUser";
        args[4] = "-projectFile";
        args[5] = "O:\\Anwendungen\\Application DirX\\70 Projekte\\06 Migration 8.7\\DATEN_TRANSFER\\Prod2Prod\\" +
                "DirX_offlineTS\\offline_TS_AD.project.xlsx";


        CmdLine cmdLine = new CmdLine();
        cmdLine.runCmd(args);


    }

    public static void testD() throws Exception {


        String[] args = new String[6];
        args[0] = "-op";
        args[1] = "export_collection";
        args[2] = "-env";
        args[3] = "DIRX_PROD_PROV_READ";
        args[4] = "-projectDir";
        args[5] = "O:\\Anwendungen\\Application DirX\\70 Projekte\\06 Migration 8.7\\DATEN_TRANSFER\\Prod2Prod\\Provisioning";


        CmdLine cmdLine = new CmdLine();
        cmdLine.runCmd(args);


    }

    public static void testF() throws Exception {
        String[] args = new String[6];
        args[0] = "-op";
        args[1] = "delete_attributes";
        args[2] = "-inputFile";
        args[3] = "O:\\Anwendungen\\Application DirX\\70 Projekte\\06 Migration 8.7\\DATEN_TRANSFER\\Prod2Prod\\OID\\OID.project.xlsx_export - Kopie.ldif";
        args[4] = "-attributes";
        args[5] = "userpassword,authpassword;orclcommonpwd,authpassword;oid,orclpassword";


        CmdLine cmdLine = new CmdLine();
        cmdLine.runCmd(args);
    }

    public static void testG() throws Exception {
        String[] args = new String[8];
        args[0] = "-op";
        args[1] = "import_collection";
        args[2] = "-env";
        args[3] = "OID_E2";
        args[4] = "-importOptions";
        args[5] = "ADD_ONLY";
        args[6] = "-inputFile";
        args[7] = "O:\\Anwendungen\\Application DirX\\70 Projekte\\06 Migration 8.7\\DATEN_TRANSFER\\Prod2Prod\\OID\\OID.project.xlsx_export.ldif";

        CmdLine cmdLine = new CmdLine();
        cmdLine.runCmd(args);
    }





    public static void main(String args[]) throws Exception {

            testD();

    }


}
