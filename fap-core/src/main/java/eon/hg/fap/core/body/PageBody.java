package eon.hg.fap.core.body;

import eon.hg.fap.core.exception.Assert;
import eon.hg.fap.core.query.support.IPageList;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"success","data"})
public class PageBody<T> implements Serializable {

    @NonNull private Integer code;
    private Boolean success;
    private String msg;
    private int page;
    private int total;
    private T data;

    public PageBody(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    /**
     * 业务处理成功
     * @return
     */
    public static <T> PageBody<T> success() {
        PageBody<T> pageBody = new PageBody<T>(ResultCode.SUCCESS.getCode());
        pageBody.setSuccess(true);
        return pageBody;
    }

    /**
     * 业务处理成功，并返回对象
     * @param o
     * @return
     */
    public static <T> PageBody<T> success(Object o) {
        PageBody<T> pageBody = new PageBody<T>(ResultCode.SUCCESS.getCode());
        pageBody.setSuccess(true);
        if (o instanceof IPageList) {
            return pageBody.addPageInfo((IPageList) o);
        } else {
            return pageBody.addObject((T) o);
        }
    }

    /**
     * 业务处理失败，返回自定义的错误提示
     * @param reason
     * @return
     */
    public static <T> PageBody<T> failed(String reason) {
        PageBody<T> pageBody = new PageBody<T>(ResultCode.CUSTOMIZE_FAIL.getCode());
        pageBody.setSuccess(false);
        return pageBody;
    }

    public static <T> PageBody<T> failed(ResultCode resultCode) {
        PageBody<T> pageBody = new PageBody<T>(resultCode);
        pageBody.setSuccess(false);
        return pageBody;
    }

    /**
     * 加载页对象
     * @param page
     * @return
     */
    public PageBody<T> addPageInfo(IPageList page) {
        this.page = page.getCurrentPage();
        this.total = page.getRowCount();
        return this.addObject((T) page.getResult());
    }

    public PageBody<T> addPageInfo(List resultList, int page, int total) {
        this.page = page;
        this.total = total;
        return this.addObject((T) resultList);
    }

    public PageBody<T> addObject(T o) {
        setData(o);
        return this;
    }
}
