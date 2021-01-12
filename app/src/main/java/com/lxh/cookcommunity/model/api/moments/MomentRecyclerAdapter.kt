package com.lxh.cookcommunity.model.api.moments

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.ItemMomentBinding
import com.lxh.cookcommunity.model.api.UserProfileModel
import com.lxh.cookcommunity.ui.fragment.commonlist.CommonListRecyclerAdapter
import com.lxh.cookcommunity.util.showGallery

class MomentRecyclerAdapter(
    val activity:ComponentActivity,
    lifecycleOwner: LifecycleOwner,
    val onCellClick: (MomentContent) -> Unit,
    val onHeaderClick: (UserProfileModel?) -> Unit,
    val onLikeClick: (MomentContent?, Int) -> Unit,
    val onCommitClick: (MomentContent) -> Unit
) : CommonListRecyclerAdapter<MomentContent, ItemMomentBinding>(
    lifecycleOwner = lifecycleOwner,
    layoutRes = R.layout.item_moment,
    hasLoadMore = true,
    baseList = emptyList()
) {

    override fun bindData(binding: ItemMomentBinding, position: Int) {
        val moment = baseList[position]
        binding.moment = moment

        //宫格图
        binding.recImg.run {
            layoutManager = getMomentsPictureLayoutManager(context, moment.images?.size ?: 1)
            adapter = MomentsListGridImageAdapter(moment.images?: emptyList()) { imgRecPosition ->
                showGallery(
                    activity,
                    moment.images,
                    imgRecPosition
                )
            }
        }

        val likeList = moment.likeList?.map { it.commentator } ?: emptyList()

        val likeSize = likeList.size
        binding.likeString = when {
            likeSize > 10 -> "等共${likeSize}人觉得很赞"
            likeSize in 1..10 -> "共${likeSize}人觉得很赞"
            else -> ""
        }
        binding.root.setOnClickListener { onCellClick(moment) }
        binding.cellAuthorPortrait.setOnClickListener { onHeaderClick(moment.profile) }
        binding.btnLike.setOnClickListener { onLikeClick(moment, position) }
        binding.btnCommit.setOnClickListener { onCommitClick(moment) }
    }

    /*//点赞或取消点赞
    @RequiresApi(Build.VERSION_CODES.N)
    fun like(commentId: Long, momentPosition: Int) {
        val moment = baseList[momentPosition]
        val changedMoment =
            moment.copy(momentCommentList = moment.momentCommentList.toMutableList().apply {
                when {
                    moment.isLikedByMe -> removeIf {
                        it.commentator.openId == SharedPrefModel.getUserModel().uid && it.commentType == COMMENT_LIKE
                    }
                    else -> add(
                        MomentComment(
                            commentId,
                            COMMENT_LIKE,
                            commentator = getUserSimpleProfile(),
                            commentDate = 0.toLong(),
                            commentReplyList = emptyList()
                        )
                    )
                }
            })
        changeData(
            changedMoment,
            momentPosition
        )
    }*/

}

fun getMomentsPictureLayoutManager(context: Context, size: Int) =
    GridLayoutManager(
        context,
        when {
            size % 3 == 0 -> 3
            size == 1 -> 1
            size == 2 -> 2
            size == 4 -> 2
            else -> 3
        }
    )

