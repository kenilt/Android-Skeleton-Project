package com.kenilt.skeleton.ui.base.paged

import com.kenilt.skeleton.managers.interfaces.IModel
import com.kenilt.skeleton.managers.interfaces.IPageable
import com.kenilt.skeleton.managers.interfaces.IPagedMapper
import com.kenilt.skeleton.model.vo.PagedCached
import java.util.concurrent.Executor

/**
 * Created by thangnguyen on 2019-07-02.
 */
abstract class LXPageKeyedDataSource<Value: IModel>(
        retryExecutor: Executor,
        pagedCached: PagedCached<Value>?,
        mapper: IPagedMapper<List<Value>, IPageable<Value>>? = null
): BasePagedKeyDataSource<Value, Value>(retryExecutor, pagedCached, mapper)
