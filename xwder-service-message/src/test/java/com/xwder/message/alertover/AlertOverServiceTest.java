package com.xwder.message.alertover;

import com.xwder.framework.utils.message.Result;
import com.xwder.massge.MessageApplication;
import com.xwder.massge.module.alertover.service.inft.AlertOverMessageServie;
import com.xwder.massge.module.wechat.service.intf.FtqqWeChatMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * alertover 发送app消息推送
 *
 * @Author: xwder
 * @Date: 2019-11-19 20:10:40
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessageApplication.class)
public class AlertOverServiceTest {

    @Autowired
    private AlertOverMessageServie alertOverMessageServie;

    /**
     * alertover 发送app消息推送
     */
    @Test
    public void sendAlertOverMessageTest() {
        String title = "剑来";
        String content = "<br>&nbsp;&nbsp;&nbsp;&nbsp;二月二，龙抬头。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;暮色里，小镇名叫泥瓶巷的僻静地方，有位孤苦伶仃的清瘦少年，此时他正按照习俗，一手持蜡烛，一手持桃枝，照耀房梁、墙壁、木床等处，用桃枝敲敲打打，试图借此驱赶蛇蝎、蜈蚣等，嘴里念念有词，是这座小镇祖祖辈辈传下来的老话：二月二，烛照梁，桃打墙，人间蛇虫无处藏。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;少年姓陈，名平安，爹娘早逝。小镇的瓷器极负盛名，本朝开国以来，就担当起“奉诏监烧献陵祭器”的重任，有朝廷官员常年驻扎此地，监理官窑事务。无依无靠的少年，很早就当起了烧瓷的窑匠，起先只能做些杂事粗活，跟着一个脾气糟糕的半路师傅，辛苦熬了几年，刚刚琢磨到一点烧瓷的门道，结果世事无常，小镇突然失去了官窑造办这张护身符，小镇周边数十座形若卧龙的窑炉，一夜之间全部被官府勒令关闭熄火。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;陈平安放下新折的那根桃枝，吹灭蜡烛，走出屋子后，坐在台阶上，仰头望去，星空璀璨。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;少年至今仍然清晰记得，那个只肯认自己做半个徒弟的老师傅，姓姚，在去年暮秋时分的清晨，被人发现坐在一张小竹椅子上，正对着窑头方向，闭眼了。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;不过如姚老头这般钻牛角尖的人，终究少数。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;世世代代都只会烧瓷一事的小镇匠人，既不敢僭越烧制贡品官窑，也不敢将库藏瓷器私自贩卖给百姓，只得纷纷另谋出路，十四岁的陈平安也被扫地出门，回到泥瓶巷后，继续守着这栋早已破败不堪的老宅，差不多是家徒四壁的惨淡场景，便是陈平安想要当败家子，也无从下手。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;当了一段时间飘来荡去的孤魂野鬼，少年实在找不到挣钱的营生，靠着那点微薄积蓄，少年勉强填饱肚子，前几天听说几条街外的骑龙巷，来了个姓阮的外乡老铁匠，对外宣称要收七八个打铁的学徒，不给工钱，但管饭，陈平安就赶紧跑去碰运气，不曾想老人只是斜瞥了他一眼，就把他拒之门外，当时陈平安就纳闷，难道打铁这门活计，不是看臂力大小，而是看面相好坏？<br><br>&nbsp;&nbsp;&nbsp;&nbsp;要知道陈平安虽然看着孱弱，但力气不容小觑，这是少年那些年烧瓷拉坯锻炼出来的身体底子，除此之外，陈平安还跟着姓姚的老人，跑遍了小镇方圆百里的山山水水，尝遍了四周各种土壤的滋味，任劳任怨，什么脏活累活都愿意做，毫不拖泥带水。可惜老姚始终不喜欢陈平安，嫌弃少年没有悟性，是榆木疙瘩不开窍，远远不如大徒弟刘羡阳，这也怪不得老人偏心，师父领进门，修行在个人，例如同样是枯燥乏味的拉坯，刘羡阳短短半年的功力，就抵得上陈平安辛苦三年的水准。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;虽然这辈子都未必用得着这门手艺，但陈平安仍是像以往一般，闭上眼睛，想象自己身前搁置有青石板和轱辘车，开始练习拉坯，熟能生巧。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;大概每过一刻钟，少年就会歇息稍许时分，抖抖手腕，如此循环反复，直到整个人彻底精疲力尽，陈平安这才起身，一边在院中散步，一边缓缓舒展筋骨。从来没有人教过陈平安这些，是他自己瞎琢磨出来的门道。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;天地间原本万籁寂静，陈平安听到一声刺耳的讥讽笑声，停下脚步，果不其然，看到那个同龄人蹲在墙头上，咧着嘴，毫不掩饰他的鄙夷神色。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;此人是陈平安的老邻居，据说更是前任监造大人的私生子，那位大人唯恐清流非议、言官弹劾，最后孤身返回京城述职，把孩子交由颇有私交情谊的接任官员，帮着看管照拂。如今小镇莫名其妙地失去官窑烧制资格，负责替朝廷监理窑务的督造大人，自己都泥菩萨过江自身难保了，哪里还顾得上官场同僚的私生子，丢下一些银钱，就火急火燎赶往京城打点关系。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;不知不觉已经沦为弃子的邻居少年，日子倒是依旧过得优哉游哉，成天带着他的贴身丫鬟，在小镇内外逛荡，一年到头游手好闲，也从来不曾为银子发过愁。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;泥瓶巷家家户户的黄土院墙都很低矮，其实邻居少年完全不用踮起脚跟，就可以看到这边院子的景象，可每次跟陈平安说话，偏偏喜欢蹲在墙头上。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;相比陈平安这个名字的粗浅俗气，邻居少年就要雅致许多，叫宋集薪，就连与他相依为命的婢女，也有个文绉绉的称呼，稚圭。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;少女此时就站在院墙那边，她有一双杏眼，怯怯弱弱。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;院门那边，有个嗓音响起，“你这婢女卖不卖？”<br><br>&nbsp;&nbsp;&nbsp;&nbsp;宋集薪愣了愣，循着声音转头望去，是个眉眼含笑的锦衣少年，站在院外，一张全然陌生的面孔。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;锦衣少年身边站着一位身材高大的老者，面容白皙，脸色和蔼，轻轻眯眼打量着两座毗邻院落的少年少女。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;老者的视线在陈平安一扫而过，并无停滞，但是在宋集薪和婢女身上，多有停留，笑意渐渐浓郁。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;宋集薪斜眼道：“卖！怎么不卖！”<br><br>&nbsp;&nbsp;&nbsp;&nbsp;那少年微笑道：“那你说个价。”<br><br>&nbsp;&nbsp;&nbsp;&nbsp;少女瞪大眼眸，满脸匪夷所思，像一头惊慌失措的年幼麋鹿。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;宋集薪翻了个白眼，伸出一根手指，晃了晃，“白银一万两！”<br><br>&nbsp;&nbsp;&nbsp;&nbsp;锦衣少年脸色如常，点头道：“好。”<br><br>&nbsp;&nbsp;&nbsp;&nbsp;宋集薪见那少年不像是开玩笑的样子，连忙改口道：“是黄金万两！”<br><br>&nbsp;&nbsp;&nbsp;&nbsp;锦衣少年嘴角翘起，道：“逗你玩的。”<br><br>&nbsp;&nbsp;&nbsp;&nbsp;宋集薪脸色阴沉。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;锦衣少年不再理睬宋集薪，偏移视线，望向陈平安，“今天多亏了你，我才能买到那条鲤鱼，买回去后，我越看越欢喜，想着一定要当面跟你道一声谢，于是就让吴爷爷带我连夜来找你。”<br><br>&nbsp;&nbsp;&nbsp;&nbsp;他丢出一只沉甸甸的绣袋，抛给陈平安，笑脸灿烂道：“这是酬谢，你我就算两清了。”<br><br>&nbsp;&nbsp;&nbsp;&nbsp;陈平安刚想要说话，锦衣少年已经转身离去。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;陈平安皱了皱眉头。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;白天自己无意间看到有个中年人，提着只鱼篓走在大街上，捕获了一尾巴掌长短的金黄鲤鱼，它在竹篓里蹦跳得厉害，陈平安只瞥了一眼，就觉得很喜庆，于是开口询问，能不能用十文钱买下它，中年人本来只是想着犒劳犒劳自己的五脏庙，眼见有利可图，就坐地起价，狮子大开口，非要三十文钱才肯卖。囊中羞涩的陈平安哪里有这么多闲钱，又实在舍不得那条金灿灿的鲤鱼，就眼馋跟着中年人，软磨硬泡，想着把价格砍到十五文，哪怕是二十文也行，就在中年人有松口迹象的时候，锦衣少年和高大老人正好路过，他们二话不说，用五十文钱买走了鲤鱼和鱼篓，陈平安只能眼睁睁看着他们扬长而去，无可奈何。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;死死盯住那对爷孙愈行愈远的背影，宋集薪收回恶狠狠的眼神后，跳下墙头，似乎记起什么，对陈平安说道：“你还记得正月里的那条四脚吗？”<br><br>&nbsp;&nbsp;&nbsp;&nbsp;陈平安点了点头。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;怎么会不记得，简直就是记忆犹新。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;按照这座小镇传承数百年的风俗，如果有蛇类往自家屋子钻，是好兆头，主人绝对不要将其驱逐打杀。宋集薪在正月初一的时候，坐在门槛上晒太阳，然后就有只俗称四脚蛇的小玩意儿，在他的眼皮子底下往屋里窜，宋集薪一把抓住就往院子里摔出去，不曾想那条已经摔得七荤八素的四脚蛇，愈挫愈勇，一次次，把从来不信鬼神之说的宋集薪给气得不行，一怒之下就把它甩到了陈平安院子，哪里想到，宋集薪第二天就在自己床底下，看到了那条盘踞蜷缩起来的四脚蛇。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;宋集薪察觉到少女扯了扯自己袖子。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;少年与她心有灵犀，下意识就将已经到了嘴边的话语，重新咽回肚子。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;他想说的是，那条奇丑无比的四脚蛇，最近额头上有隆起，如头顶生角。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;宋集薪换了一句话说出口，“我和稚圭可能下个月就要离开这里了。”<br><br>&nbsp;&nbsp;&nbsp;&nbsp;陈平安叹了口气，“路上小心。”<br><br>&nbsp;&nbsp;&nbsp;&nbsp;宋集薪半真半假道：“有些物件我肯定搬不走，你可别趁我家没人，就肆无忌惮地偷东西。”<br><br>&nbsp;&nbsp;&nbsp;&nbsp;陈平安摇了摇头。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;宋集薪蓦然哈哈大笑，用手指点了点陈平安，嬉皮笑脸道：“胆小如鼠，难怪寒门无贵子，莫说是这辈子贫贱任人欺，说不定下辈子也逃不掉。”<br><br>&nbsp;&nbsp;&nbsp;&nbsp;陈平安默不作声。<br><br>&nbsp;&nbsp;&nbsp;&nbsp;各自返回屋子，陈平安关上门，躺在坚硬的木板床上，贫寒少年闭上眼睛，小声呢喃道：“碎碎平，岁岁安，碎碎平安，岁岁平安……”<br><br>&nbsp;&nbsp;&nbsp;&nbsp;-------------<br><br>&nbsp;&nbsp;&nbsp;&nbsp;ps1:雪中的一个近二十万字的番外在微信里面更新了，微信公众号是：fenghuo1985<br><br>&nbsp;&nbsp;&nbsp;&nbsp;ps2:还没上传就一百多位盟主，你们威武...<br><br>&nbsp;&nbsp;&nbsp;&nbsp;ps3:好久不见，剑来！     <br>     '<br>     <br>    ";
        Result result = alertOverMessageServie.sendStrMessge(title,content);
    }

}
