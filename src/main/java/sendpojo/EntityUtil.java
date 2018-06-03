package sendpojo;

import java.io.*;

/**
 * @author zonzie
 * @date 2018/4/17 16:57
 */
public class EntityUtil {


    public static byte[] objectToBytes(Object obj){
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bo);
            oos.writeObject(obj);
            oos.flush();
            oos.close();
            bo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bo.toByteArray();
    }

    public static MsgEntity bytesToObject(byte[] bytes){
        MsgEntity document = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream (bytes);
            ObjectInputStream ois = new ObjectInputStream (bis);
            document = (MsgEntity) ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return document;
    }


//    public static byte[] objectToBytes(Object obj){
//        byte[] bytes = null;
//        ByteArrayOutputStream bo = null;
//        ObjectOutputStream oo = null;
//        try {
//            bo = new ByteArrayOutputStream();
//            oo = new ObjectOutputStream(bo);
//            oo.writeObject(obj);
//            bytes = bo.toByteArray();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                if(bo!=null){
//                    bo.close();
//                }
//                if(oo!=null){
//                    oo.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return bytes;
//    }
//
//    public static Object bytesToObject(byte[] bytes){
//        Object obj = null;
//        ByteArrayInputStream bi = null;
//        ObjectInputStream oi = null;
//        try {
//            bi =new ByteArrayInputStream(bytes);
//            oi =new ObjectInputStream(bi);
//            obj = oi.readObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                if(bi!=null){
//                    bi.close();
//                }
//                if(oi!=null){
//                    oi.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return obj;
//    }



}
