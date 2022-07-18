package eu.darken.myperm.apps.ui.details.items

import android.content.res.ColorStateList
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import eu.darken.myperm.R
import eu.darken.myperm.apps.core.features.UsesPermission
import eu.darken.myperm.apps.core.features.UsesPermission.Status
import eu.darken.myperm.apps.ui.details.AppDetailsAdapter
import eu.darken.myperm.common.lists.BindableVH
import eu.darken.myperm.databinding.AppsDetailsPermissionUnknownItemBinding
import eu.darken.myperm.permissions.core.tryLabel
import eu.darken.myperm.permissions.core.types.UnknownPermission

class UnknownPermissionVH(parent: ViewGroup) :
    AppDetailsAdapter.BaseVH<UnknownPermissionVH.Item, AppsDetailsPermissionUnknownItemBinding>(
        R.layout.apps_details_permission_unknown_item,
        parent
    ), BindableVH<UnknownPermissionVH.Item, AppsDetailsPermissionUnknownItemBinding> {

    override val viewBinding = lazy { AppsDetailsPermissionUnknownItemBinding.bind(itemView) }

    override val onBindData: AppsDetailsPermissionUnknownItemBinding.(
        item: Item,
        payloads: List<Any>
    ) -> Unit = { item, _ ->
        val permission = item.permission

        identifier.text = permission.id.value
        label.apply {
            text = permission.tryLabel(context)
            isGone = text.isNullOrEmpty()
        }

        statusIcon.apply {
            val (iconRes, tintRes) = when (item.appPermission.status) {
                Status.GRANTED -> R.drawable.ic_baseline_check_circle_24 to R.color.status_positive_1
                Status.GRANTED_IN_USE -> R.drawable.ic_baseline_check_circle_24 to R.color.status_positive_1
                Status.DENIED -> R.drawable.ic_baseline_remove_circle_24 to R.color.status_negative_1
                Status.UNKNOWN -> R.drawable.ic_baseline_question_mark_24 to R.color.permission_status_unknown
            }
            setImageResource(iconRes)
            imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, tintRes))
        }

        root.setOnClickListener { item.onItemClicked(item) }
    }

    data class Item(
        val appPermission: UsesPermission,
        val permission: UnknownPermission,
        val onItemClicked: (Item) -> Unit,
    ) : AppDetailsAdapter.Item {
        override val stableId: Long
            get() = permission.id.hashCode().toLong()
    }
}