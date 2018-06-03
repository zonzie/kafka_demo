package sendpojo;

import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

import java.io.Serializable;

/**
 * @author zonzie
 * @date 2018/4/17 16:58
 */
public class MsgEntity implements Serializable,Encoder<MsgEntity> {

    /**图片id*/
    private int id;
    /**图片url*/
    private String url;

    /**
     * 构造函数
     * @param id 图片id
     * @param url 图片url
     */
    public MsgEntity(int id, String url){
        this.id = id;
        this.url = url;
    }
    /**
     * 构造函数，kafka支持
     * @param verifiableProperties
     */
    public MsgEntity(VerifiableProperties verifiableProperties){}

    public MsgEntity() {
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public byte[] toBytes(MsgEntity msgEntity) {
        return EntityUtil.objectToBytes(msgEntity);
    }
}
