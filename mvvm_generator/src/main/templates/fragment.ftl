package ${basePackage}.${fragmentPackage}.${classFolderName}

import ${basePackage}.R
import ${basePackage}.databinding.${bindingName}
import ${basePackage}.${fragmentBasePackage}.BaseFragment

class ${fragmentName} : BaseFragment<${bindingName}, ${viewModelName}>(
    ${viewModelName}::class.java, layoutRes = R.layout.${layoutName}
) {


    override fun initView() {

    }


    override fun initData() {

    }
}