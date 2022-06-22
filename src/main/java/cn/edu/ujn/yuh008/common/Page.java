package cn.edu.ujn.yuh008.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private static final long serialVersionUID = 8545996863226528798L;
    protected long total = 0L;
    protected Integer pageSize = 10;
    protected Integer pageNum = 1;
    @JsonIgnore
    protected T parame;
    protected List<T> list;
    private String msg;
    private Page() {
    }

    public Page(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        PageHelper.startPage(pageNum, pageSize).setReasonable(Boolean.TRUE);
    }

    public Page(int pageNum, int pageSize, T parame) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.parame = parame;
        PageHelper.startPage(pageNum, pageSize).setReasonable(Boolean.TRUE);
    }

    public void setList(List<T> list) {
        PageInfo<T> tPageInfo = new PageInfo(list);
        this.list = tPageInfo.getList();
        this.total = tPageInfo.getTotal();
        this.pageNum = tPageInfo.getPages();
    }



    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Page)) {
            return false;
        } else {
            Page<?> other = (Page)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getTotal() != other.getTotal()) {
                return false;
            } else {
                label61: {
                    Object this$pageSize = this.getPageSize();
                    Object other$pageSize = other.getPageSize();
                    if (this$pageSize == null) {
                        if (other$pageSize == null) {
                            break label61;
                        }
                    } else if (this$pageSize.equals(other$pageSize)) {
                        break label61;
                    }

                    return false;
                }

                label54: {
                    Object this$pageNum = this.getPageNum();
                    Object other$pageNum = other.getPageNum();
                    if (this$pageNum == null) {
                        if (other$pageNum == null) {
                            break label54;
                        }
                    } else if (this$pageNum.equals(other$pageNum)) {
                        break label54;
                    }

                    return false;
                }

                Object this$parame = this.getParame();
                Object other$parame = other.getParame();
                if (this$parame == null) {
                    if (other$parame != null) {
                        return false;
                    }
                } else if (!this$parame.equals(other$parame)) {
                    return false;
                }

                Object this$list = this.getList();
                Object other$list = other.getList();
                if (this$list == null) {
                    if (other$list != null) {
                        return false;
                    }
                } else if (!this$list.equals(other$list)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Page;
    }

    public int hashCode() {
        int result = 1;
        long $total = this.getTotal();
        result = result * 59 + (int)($total >>> 32 ^ $total);
        Object $pageSize = this.getPageSize();
        result = result * 59 + ($pageSize == null ? 43 : $pageSize.hashCode());
        Object $pageNum = this.getPageNum();
        result = result * 59 + ($pageNum == null ? 43 : $pageNum.hashCode());
        Object $parame = this.getParame();
        result = result * 59 + ($parame == null ? 43 : $parame.hashCode());
        Object $list = this.getList();
        result = result * 59 + ($list == null ? 43 : $list.hashCode());
        return result;
    }

    public String toString() {
        return "Page(total=" + this.getTotal() + ", pageSize=" + this.getPageSize() + ", pageNum=" + this.getPageNum() + ", parame=" + this.getParame() + ", list=" + this.getList() + ")";
    }
}

