# 这是一个示例 Python 脚本。

# 按 Shift+F10 执行或将其替换为您的代码。
# 按 双击 Shift 在所有地方搜索类、文件、工具窗口、操作和设置。
print(type(123))
print('hello'+"15415")
name='你还'
def print_hi(name):
    # 在下面的代码行中使用断点来调试脚本。
    print(f'Hi, {name}')  # 按 Ctrl+F8 切换断点。
# age=input('请输入')
# age=int(input('请输入年龄'))
# if age>= 18:
#     print('你已成年,游玩愉快')
# elif age>=16:
#     print('你还未成年,回去吧!')
# else:
#     print('你还未成年,回去吧22212!')
print(range(20))
# 按间距中的绿色按钮以运行脚本。
if __name__ == '__main__':
    print_hi('PyCharm')
    print('hello')
else:
    print('hello')
# d数字,F浮点数,s字符串变量占位
nid = "你好,请告诉我你是谁"
uid = 213231
print("你好%s" % uid)
print("你好%10d%2s" % (uid, nid))
print(f"这是一个测试字符串{uid}{nid}")
# print(f"这是一个测试字符串{uid,nid}")
# 访问 https://www.jetbrains.com/help/pycharm/ 获取 PyCharm 帮助
def check2(x,y,z):
    """

    :param x:
    :param y:
    :param z:
    :return:
    """


    import random
    rand=random.randint(10,20)
    print(x+y+z+rand)
    return x+y+z+rand
not1= check2(221,10,20)
print(not1)
# 列表
list=['list1','list2','list3']
print(type(list))
print(list[1])
for x in list:
    print(f"元素第{x}个")
# 元组
tuple=('list1','list2','list3')
print(type(tuple))
print(tuple[0])
# 字典
tinydict = {'name': 'runoob', 'likes': 123, 'url': 'www.runoob.com'}
print(type(tinydict))
print(tinydict['name'])
# 集合
basket = {'apple', 'orange', 'apple', 'pear', 'orange', 'banana'}
print(type(basket))
print(basket)
import time  # 引入time模块

ticks = time.time()
print ("当前时间戳为:", ticks)