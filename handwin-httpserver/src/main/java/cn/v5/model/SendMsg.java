package cn.v5.model;

import java.util.Arrays;
import java.util.Date;

import cn.v5.framework.jdbc.Model;

/** 
 * @author qgan
 * @version 2013年12月17日 上午11:02:33
 */
public class SendMsg extends Model<SendMsg> {
    public Long id;

    public String sender;

    public String receiver;

    public String receiverType;

    public Date receiveTime;

    public Date sendTime;

    public byte[] content;

    /**
     * 表示：是否失效 1 已发送  2 失效
     */
    public String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SentMsg [id=" + id + ", sender=" + sender + ", receiver="
				+ receiver + ", receiverType=" + receiverType
				+ ", receiveTime=" + receiveTime + ", sendTime=" + sendTime
				+ ", content=" + Arrays.toString(content) + ", status="
				+ status + "]";
	}
}