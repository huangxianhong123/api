package com.liuc.server.api.common;

import com.liuc.server.api.util.StringUtil;
import org.dom4j.util.StringUtils;

import java.util.List;

public class SqlProvider {

    public static String queryLonAndLat(String longitude, String latitude) {
        return "SELECT  *," +
                "        ROUND(6378.138 * 2 * ASIN(" +
                "        SQRT(POW(" +
                "        SIN(" +
                "        (" + latitude + " * PI() / 180 - latitude * PI() / 180) / 2" +
                "        ),2)" +
                "         + COS(" + latitude + " * PI() / 180) * COS(latitude * PI() / 180) * POW(" +
                "        SIN(" +
                "        (" + longitude + " * PI() / 180 - longitude * PI() / 180 ) / 2" +
                "        ),2" +
                "        )" +
                "        )) * 1000) AS distance FROM store ORDER BY distance ASC";
    }

    public static String queryLonAndLatIds(String longitude, String latitude, String ids) {
        return "SELECT  *," +
                "        ROUND(6378.138 * 2 * ASIN(" +
                "        SQRT(POW(" +
                "        SIN(" +
                "        (" + latitude + " * PI() / 180 - latitude * PI() / 180) / 2" +
                "        ),2)" +
                "         + COS(" + latitude + " * PI() / 180) * COS(latitude * PI() / 180) * POW(" +
                "        SIN(" +
                "        (" + longitude + " * PI() / 180 - longitude * PI() / 180 ) / 2" +
                "        ),2" +
                "        )" +
                "        )) * 1000) AS distance FROM store where FIND_IN_SET(store_id,+" + "'" + ids + "" + "') ORDER BY distance ASC";
    }

    public static String queryUser(String userCode) {
        String sql = "SELECT * FROM BD_PSNDOC_INFO WHERE CODE = '" + userCode + "'";
        return sql;
    }



    public static String getBankList(Integer accclass) {
        StringBuffer sql = new StringBuffer("select bd_bankaccbas.accclass,bd_bankaccsub.pk_bankaccsub as id,bd_bankaccsub.accnum,bd_bankaccsub.accname,\n" +
                "bd_banktype.pk_banktype as banktypeid,bd_banktype.code as banktypecode,bd_banktype.name as bankname,\n" +
                "bd_bankdoc.pk_bankdoc as bdnkdocid,bd_bankdoc.code as bankdoccode,bd_bankdoc.name as bankdocname,\n" +
                "bd_bankaccbas.pk_org,bd_bankaccbas.enablestate\n" +
                " from bd_bankaccsub left join bd_bankaccbas on bd_bankaccsub.pk_bankaccbas = bd_bankaccbas.pk_bankaccbas\n" +
                "left join bd_banktype on bd_bankaccbas.pk_banktype = bd_banktype.pk_banktype\n" +
                "left join bd_bankdoc on bd_bankaccbas.pk_bankdoc = bd_bankdoc.pk_bankdoc\n" +
                "where nvl(bd_bankaccsub.dr,0)=0 and nvl(bd_bankaccbas.dr,0)=0");

        if(accclass != null){
            sql.append(" And bd_bankaccbas.accclass ="+ accclass);
        }
        return sql.toString();
    }


    public static String getSettlementList(String name) {
        String sql = "select pk_balatype as id ,code,name,enablestate from bd_balatype where nvl(dr,0)=0";
        if(StringUtil.notEmpty(name)){
            sql +=  " and name = '" + name + "'";
        }
        return sql;
    }

    public static String getTrafficList() {
        String sql = "select id,code,name as name from oa_jtgj ";
        return sql;
    }


//    public static String listDepartment(String code) {
//        StringBuffer sql = new StringBuffer("select org_dept.pk_org as orgid,org_dept.pk_dept as id ,org_dept.code ,org_dept.name,fatherdept.code as fathercode,fatherdept.name as fathername ,\n" +
//                "bd_psndoc.code as maindoccode,bd_psndoc.name as maindocname,org_dept.pk_fatherorg\n" +
//                "from org_dept \n" +
//                "left join org_dept fatherdept on org_dept.pk_fatherorg = fatherdept.pk_dept \n" +
//                "left join bd_psndoc on org_dept.principal = bd_psndoc.pk_psndoc \n" +
//                "left join org_orgs org on org.pk_org = org_dept.pk_org \n" +
//                "where nvl(org_dept.dr,0)=0 ");
//                if(StringUtil.notEmpty(code)){
//                    sql.append("and org.code = '" + code + "'");
//                }
//        return sql.toString();
//    }


    public static String listDepartment(String fathercode,String orgid) {
        StringBuffer sql = new StringBuffer("select * from oa_dept where 1=1 ");
        if(StringUtil.notEmpty(fathercode)){
            sql.append(" and fathercode = '" + fathercode + "'");
        }

        if(StringUtil.notEmpty(orgid)){
            sql.append(" and orgid = '" + orgid + "'");
        }

//        if (StringUtil.notEmpty(keyword)) {
//            sql.append(" And (code like '%" + keyword + "%'  or name like '%" + keyword + "%' )");
//        }
        return sql.toString();
    }

    public static String getDistinctPid(String orgid,String keyword) {
        StringBuffer sql = new StringBuffer("select * from oa_dept where 1=1");
        if(StringUtil.notEmpty(orgid)){
            sql.append(" and orgid = '" + orgid + "'");
        }

        if (StringUtil.notEmpty(keyword)) {
            sql.append(" And (code like '%" + keyword + "%'  or name like '%" + keyword + "%' )");

        }else{
            sql.append(" And fathercode is null ");

        }

        return sql.toString();
    }

    //获取业务单元数据
    public static String getAllBussinessUnit(String keyword) {
        String sql = "select org_orgs.pk_org as id ,org_orgs.code ,org_orgs.name ,fatherorg.code as fatherCode,fatherorg.name as fatherName from org_orgs\n" +
                "left join org_orgs fatherorg on org_orgs.pk_fatherorg = fatherorg.pk_org\n" +
                "where org_orgs.isbusinessunit='Y'";

        if (StringUtil.notEmpty(keyword)) {
            sql += " And (org_orgs.code like '%" + keyword + "%'  or org_orgs.name like '%" + keyword + "%' )";

        }

        return sql;

    }
    //获取业务单元数据
    public static String getUserInfoByCode(String code,String deptCode) {
        StringBuffer sql = new StringBuffer("select a.pk_group as pk_group,a.pk_org as orgid,bd_psndoc.pk_psndoc as id ,bd_psndoc.code ,bd_psndoc.name,bd_psndoc.enablestate,\n" +
                "org_dept.code as deptcode,org_dept.name as deptname ,org_orgs.name AS orgname,org_orgs.code AS orgcode,sm_user.user_code  AS userCode,\n" +
                "sm_user.cuserid AS userid from bd_psndoc \n" +
                "left join(select t.pk_group,t.pk_org,t.pk_psndoc, t.pk_dept\n" +
                "from (select r.pk_group,r.pk_org,r.pk_psndoc,r.pk_dept,\n" +
                "row_number() over(partition by r.pk_psndoc order by r.indutydate  desc) rn\n" +
                "from bd_psnjob r where nvl(dr,0)=0 and r.ismainjob='Y' and nvl(r.enddutydate,'~')='~'\n" +
                ") t where t.rn = 1\n" +
                ")a on bd_psndoc.pk_psndoc =a.pk_psndoc\n" +
                "left join org_dept on a.pk_dept = org_dept.pk_dept\n" +
                "LEFT JOIN org_orgs ON a.pk_org = org_orgs.pk_org \n" +
                "LEFT JOIN sm_user ON a.pk_psndoc = sm_user.pk_psndoc\n" +
                "where nvl(bd_psndoc.dr,0)=0 ");
        if(StringUtil.notEmpty(code)){
            sql.append(" And  sm_user.user_code = '" + code + "'");
        }
        if(StringUtil.notEmpty(deptCode)){
            sql.append(" And  org_dept.code = '" + deptCode + "'");
        }
        return sql.toString();

    }

    //获取业务单元数据
    public static String getCurrency() {
        StringBuffer sql = new StringBuffer("select pk_currtype as id ,code,name from bd_currtype where nvl(dr,0)=0");

        return sql.toString();

    }
    public static String getPersonBankList(String code,String accnum) {
        StringBuffer sql = new StringBuffer("select psndoc.pk_psndoc,psndoc.code,psndoc.name,psnacc.pk_bankaccsub,bankaccview.accnum,bankaccview.accname,bankaccview.bankdocname from bd_psnbankacc psnacc \n" +
                "left join bd_psndoc psndoc on (psnacc.pk_psndoc = psndoc.pk_psndoc )\n" +
                "left join oa_bankacc bankaccview on psnacc.pk_bankaccsub =bankaccview.id \n" +
                "where nvl(psnacc.dr,0)=0 and nvl(psnacc.pk_bankaccsub,'~')<>'~'");

        if(StringUtil.notEmpty(code)){
            sql.append(" And psndoc.code ='" + code + "'");
        }

        if(StringUtil.notEmpty(accnum)){
            sql.append(" and accnum ='" + accnum + "'");
        }

        return sql.toString();
    }

    public static String getCostList() {
        StringBuffer sql = new StringBuffer("select pk_defdoc as id ,code,name,enablestate from bd_defdoc where nvl(dr,0)=0 and pk_defdoclist='1001111000000004M5C5'");

        return sql.toString();
    }

    public static String getChargeList(String userId,String orgId,String djrq,String name,String startTime,String endTime) {
        StringBuffer sql = new StringBuffer("select * from oa_rushed where 1=1");

        if(StringUtil.notEmpty(userId)){
            sql.append(" and  jkbxr ='" + userId + "'");
        }
        if(StringUtil.notEmpty(orgId)){
            sql.append(" and  dwbm ='" + orgId + "'");
        }
        if(StringUtil.notEmpty(djrq)){
            sql.append(" and djrq <='" + djrq + "'");
        }

        if (StringUtil.notEmpty(name)) {
            sql.append(" and jkbxr_name like '%" + name + "%'");
        }

        if (StringUtil.notEmpty(startTime) && StringUtil.notEmpty(endTime)) {
            sql.append(" And djrq BETWEEN "+ "'" + startTime + "" + "'" +"and"+ "'" + endTime + "" + "'");
        }

        return sql.toString();
    }


    public static String getPaymentProjectList(String name) {
        StringBuffer sql = new StringBuffer("select code,name from bd_inoutbusiclass where 1=1 ");
        if (StringUtil.notEmpty(name)) {
            sql.append(" and name ='" + name + "'");
        }
        return sql.toString();
    }


    public static String getPaymentName(String name) {
        StringBuffer sql = new StringBuffer("select code,name from bd_inoutbusiclass where 1=1  and  name ='" + name + "' ");

        return sql.toString();
    }


    public static String getMySubmitList(String userId,String keyWord,String spzt,String jkbxr,Integer time_type,String jylx_name) {

       String sql = "select * from oa_bxjkhk where 1=1 ";
                if (StringUtil.notEmpty(userId)) {
                    sql += " and creator ='" + userId + "'";
                }
                if (StringUtil.notEmpty(spzt)) {
                    if("0".equals(spzt)){
                        String status = "0,2,3";
                        List<String> list = StringUtil.changeIdsToStrList(status);
                        String join = String.join("','", list);
                        join = "'" + join + "'";
                        sql += "And spzt in (" + join + ")";
                    }else{
                        sql += " and spzt = '" + spzt + "'";
                    }
                }
                if (StringUtil.notEmpty(keyWord)) {
                    sql += " And (jkbxr like '%" + keyWord + "%'  or djbh like '%" + keyWord + "%' ) ";
                }

                if (StringUtil.notEmpty(jkbxr)) {
                    sql += " and jkbxr like '%" + jkbxr + "%'";
                }
//                if (StringUtil.notEmpty(startTime) && StringUtil.notEmpty(endTime)) {
//                    sql += " And djrq BETWEEN "+ "'" + startTime + "" + "'" +"and"+ "'" + endTime + "" + "'";
//                }
                if (StringUtil.notEmpty(jylx_name)) {
                    sql += " and jylx_name ='" + jylx_name + "'";
                }


            if(time_type != null){
                if(time_type ==0){
                    sql += " order by djrq desc  ";
                }else{
                    sql += " order by djrq asc   ";
                }
            }else{
                sql += " order by creationtime desc  ";
            }
        return sql;
    }

    public static String getSubmitDetail(String djbh) {

        String sql = "select * from oa_bxjkhk  where 1=1 ";
                if (StringUtil.notEmpty(djbh)) {
                    sql += " and djbh = '" + djbh + "'";
                }
        return sql;
    }

    public static String getTableDetail(String djbh,String tableCode) {

        String sql = " select * from oa_bxjkhk_item  where 1=1  ";
                if (StringUtil.notEmpty(djbh)) {
                    sql += " and djbh = '" + djbh + "'";
                }
                if (StringUtil.notEmpty(tableCode)) {
                    sql += " and  tableCode = '" + tableCode + "'";
                }
        return sql;
    }


    public static String getRepaymentDetail(String djbh) {

        String sql = "  select * from oa_cxmx where  1=1  ";
        if (StringUtil.notEmpty(djbh)) {
            sql += " and bxdjbh = '" + djbh + "'";
        }

        return sql;
    }

    public static String getApproveList(String djbh) {

        String sql = "select * from oa_bxjkhk_spjd where 1=1  ";
        if (StringUtil.notEmpty(djbh)) {
            sql += " and BILLNO = '" + djbh + "'";
        }
        sql +=  "  order by senddate,dealdate asc, dealtimemillis asc,APPROVERESULT asc";
        return sql;
    }


    public static String getMyApproveList(String userId,String keyWord,String spzt,String jkbxr,Integer time_type,String jylx_name,String statusType) {

        String sql = "select * from oa_bxjkhk where 1=1 ";
        if (StringUtil.notEmpty(userId)) {
            sql += " and creator ='" + userId + "'";
        }

        if(StringUtil.isEmpty(statusType)){
            if (StringUtil.notEmpty(spzt)) {
                if("0".equals(spzt)){
                    String status = "0,3";
                    List<String> list = StringUtil.changeIdsToStrList(status);
                    String join = String.join("','", list);
                    join = "'" + join + "'";
                    sql += "And spzt in (" + join + ")";
                }else{
                    sql += " and spzt = '" + spzt + "'";
                }
            }
        }else{
            if("1".equals(spzt)){
                sql += " and spzt = '" + 9 + "'"; //查不到数据
            }else{
                sql += " and spzt = '" + statusType + "'";
            }

        }

        if (StringUtil.notEmpty(keyWord)) {
            sql += " And (jkbxr like '%" + keyWord + "%'  or djbh like '%" + keyWord + "%' ) ";
        }

        if (StringUtil.notEmpty(jkbxr)) {
            sql += " and jkbxr like '%" + jkbxr + "%'";
        }
//        if (StringUtil.notEmpty(startTime) && StringUtil.notEmpty(endTime)) {
//            sql += " And djrq BETWEEN "+ "'" + startTime + "" + "'" +"and"+ "'" + endTime + "" + "'";
//        }

        if (StringUtil.notEmpty(jylx_name)) {
            sql += " and jylx_name ='" + jylx_name + "'";
        }

        if(time_type != null){
            if(time_type ==0){
                sql += " order by djrq desc  ";
            }else{
                sql += " order by djrq asc   ";
            }
        }else{
            sql += " order by creationtime desc  ";
        }

        return sql;
    }

    public static String getInMyApproveList(String userId,String keyWord,List<String> spzt,String jkbxr,Integer time_type,String jylx_name) {

        String joinRank = String.join("','", spzt);
        joinRank = "'" + joinRank + "'";


        String sql = "select * from oa_bxjkhk_spjxz  where 1=1 and ischeck !=4 and nvl(APPROVERESULT,'N') = 'N'  and spzt in (" + joinRank + ")";
        if (StringUtil.notEmpty(userId)) {
            sql += " and creator ='" + userId + "'";
        }
        if (StringUtil.notEmpty(keyWord)) {
            sql += " And (jkbxr like '%" + keyWord + "%'  or djbh like '%" + keyWord + "%' ) ";
        }

        if (StringUtil.notEmpty(jkbxr)) {
            sql += " and jkbxr like '%" + jkbxr + "%'";
        }
//        if (StringUtil.notEmpty(startTime) && StringUtil.notEmpty(endTime)) {
//            sql += " And djrq BETWEEN "+ "'" + startTime + "" + "'" +"and"+ "'" + endTime + "" + "'";
//        }
        if (StringUtil.notEmpty(jylx_name)) {
            sql += " and jylx_name ='" + jylx_name + "'";
        }

        if(time_type != null){
            if(time_type ==0){
                sql += " order by djrq desc  ";
            }else{
                sql += " order by djrq asc   ";
            }
        }else{
            sql += " order by creationtime desc  ";
        }
        return sql;
    }

    public static String getReimtypeList(String name) {

        String sql = "select pk_reimtype ,code,name from er_reimtype where  nvl(dr, 0)= 0 ";

        if (StringUtil.notEmpty(name)) {
            sql += " and name ='" + name + "'";
        }


        return sql;
    }

    public static String getZdlxList(String id) {

        String sql = "select bd_defdoc.pk_defdoc as pk_reimtype,bd_defdoc.code,bd_defdoc.name from bd_defdoclist left join bd_defdoc on bd_defdoclist.pk_defdoclist = bd_defdoc.pk_defdoclist where bd_defdoclist.name = '招待类型' and nvl(bd_defdoc.dr, 0)= 0  ";

        if (StringUtil.notEmpty(id)) {
            sql += " and bd_defdoc.pk_defdoc='" + id + "'";
        }

        return sql;
    }



    public static String getCurrtypeCode(String code) {

        String sql = "select * from oa_currtype where 1=1 ";

        if (StringUtil.notEmpty(code)) {
            sql += " and code ='" + code + "'";
        }
        return sql;
    }

    public static String getEnumvalueCode(String code,String name) {

        String sql = "select id,code,name as name from oa_jtgj where 1=1 ";

        if (StringUtil.notEmpty(code)) {
            sql += " and code ='" + code + "'";
        }

        if (StringUtil.notEmpty(name)) {
            sql += " and name ='" + name + "'";
        }

        return sql;
    }


    public static String getReimtypeCode(String code) {

        String sql = "select * from oa_djkzgz  where 1=1";

        if (StringUtil.notEmpty(code)) {
            sql += " and code ='" + code + "'";
        }

        return sql;
    }

    public static String getReimtypeListCode(String id) {

        String sql = "select code, name, pk_reimtype from er_reimtype \n" +
                "where (nvl(dr, 0)= 0 and pk_group = '00011110000000000HT7') \n" +
                "  and pk_reimtype in ((select er_reimtype1.pk_reimtype from er_reimtype er_reimtype1 \n" +
                "      where \n" +
                "        ((exists(select 1 from bd_crossrestdata t where \n" +
                "                er_reimtype1.pk_reimtype = t.data \n";

                if (StringUtil.notEmpty(id)) {
                    sql += " and t.pk_restraint ='" + id + "'";
                }

              sql +=  "        )))\n" +
                "    )) and (inuse = 'N') order by code";


        return sql;
    }


    public static String getDefdocCode(String id) {

        String sql = " select pk_defdoc as id ,code, name from bd_defdoc   where pk_defdoclist='1001111000000004M5C5'\n" +
                " and(nvl(dr, 0)= 0 and pk_group = '00011110000000000HT7') \n" +
                " and pk_defdoc in ((select bd_defdoc1.pk_defdoc from bd_defdoc bd_defdoc1 where pk_defdoclist='1001111000000004M5C5'\n" +
                "    and((exists(select 1 from  bd_crossrestdata t where \n" +
                "                bd_defdoc.pk_defdoc = t.data ";


        if (StringUtil.notEmpty(id)) {
            sql += " and t.pk_restraint ='" + id + "'";
        }

        sql +=     "            )) ))) order by code";



        return sql;
    }

    public static String getDefdocId(String code,String id) {

        String sql = " select pk_defdoc as id ,code,name,enablestate from bd_defdoc where nvl(dr,0)=0 and pk_defdoclist='1001111000000004M5C5' ";


        if (StringUtil.notEmpty(code)) {
            sql += "  and code ='" + code + "'";
        }

        if (StringUtil.notEmpty(id)) {
            sql += "  and pk_defdoc ='" + id + "'";
        }


        return sql;
    }


    public static String getOrgCode(String name) {

        String sql = "select * from oa_org where 1=1 ";

        if (StringUtil.notEmpty(name)) {
            sql += " and name ='" + name + "'";
        }

        return sql;
    }

    public static String getDeptCode(String name,String orgId) {

        String sql = "select * from oa_dept where 1=1 ";

        if (StringUtil.notEmpty(name)) {
            sql += " and name ='" + name + "'";
        }

        if (StringUtil.notEmpty(orgId)) {
            sql += " and orgid ='" + orgId + "'";
        }

        return sql;
    }

    public static String getUserCode(String name) {

        String sql = "select * from oa_psndoc where 1=1 ";

        if (StringUtil.notEmpty(name)) {
            sql += " and name ='" + name + "'";
        }

        return sql;
    }

    public static String getFeiYongFenTanMingXl(String djbh) {

        String sql = "select * from oa_ftmx  where 1=1";

        if (StringUtil.notEmpty(djbh)) {
            sql += " and djbh ='" + djbh + "'";
        }

        return sql;
    }


    public static String getFileUrl(String id) {

        String sql = "select * from cf_urli  where 1=1 ";
        if (StringUtil.notEmpty(id)) {
            sql += " and idi = '" + id + "'";
        }
        return sql;
    }


    public static String getUserId(String name) {
        StringBuffer sql = new StringBuffer("select cuserid, user_name as name from sm_user where  1=1");

        if(StringUtil.notEmpty(name)){
            sql.append(" and  user_name ='" + name + "'");
        }

        return sql.toString();
    }


    public static String getBxjkhkListY(String userId,String keyWord,String jkbxr,Integer time_type,String jylx_name) {

        String sql = "select * from oa_bxjkhk_spjxz where 1=1 and APPROVERESULT = 'Y' and ischeck !=4 ";
        if (StringUtil.notEmpty(userId)) {
            sql += " and creator ='" + userId + "'";
        }

        if (StringUtil.notEmpty(keyWord)) {
            sql += " And (jkbxr like '%" + keyWord + "%'  or djbh like '%" + keyWord + "%' ) ";
        }

        if (StringUtil.notEmpty(jkbxr)) {
            sql += " and jkbxr like '%" + jkbxr + "%'";
        }

        if (StringUtil.notEmpty(jylx_name)) {
            sql += " and jylx_name ='" + jylx_name + "'";
        }

        if(time_type != null){
            if(time_type ==0){
                sql += " order by djrq desc  ";
            }else{
                sql += " order by djrq asc   ";
            }
        }else{
            sql += " order by creationtime desc  ";
        }

        return sql;
    }

    public static String getBxjkhkListY(String userId,String djbh) {

        String sql = "select * from oa_bxjkhk_spjxz where 1=1 and APPROVERESULT = 'Y' and ischeck !=4 ";
        if (StringUtil.notEmpty(userId)) {
            sql += " and creator ='" + userId + "'";
        }

        if (StringUtil.notEmpty(djbh)) {
            sql += " and djbh = '" + djbh + "'";
        }

        return sql;
    }

    public static String getMyAuditList(String userId,String keyWord,String spzt,String jkbxr,Integer time_type,String jylx_name,String statusType) {

        String sql = "select * from oa_bxjzhk_todolist where 1=1 ";
        if (StringUtil.notEmpty(userId)) {
            sql += " and checkman ='" + userId + "'";
        }

        if("0".equals(spzt)){
            sql += " and approveresult is null";
        }else{
            sql += " and approveresult is not null";
        }

        if (StringUtil.notEmpty(keyWord)) {
            sql += " And (jkbxr like '%" + keyWord + "%'  or djbh like '%" + keyWord + "%' ) ";
        }

        if (StringUtil.notEmpty(jkbxr)) {
            sql += " and jkbxr like '%" + jkbxr + "%'";
        }
//        if (StringUtil.notEmpty(startTime) && StringUtil.notEmpty(endTime)) {
//            sql += " And djrq BETWEEN "+ "'" + startTime + "" + "'" +"and"+ "'" + endTime + "" + "'";
//        }

        if (StringUtil.notEmpty(jylx_name)) {
            sql += " and jylx_name ='" + jylx_name + "'";
        }

        if(time_type != null){
            if(time_type ==0){
                sql += " order by djrq desc  ";
            }else{
                sql += " order by djrq asc   ";
            }
        }else{
            if("0".equals(spzt)){
                sql += " order by senddate desc";
            }else{
                sql += " order by dealdate desc ";
            }
        }

        return sql;
    }

}
