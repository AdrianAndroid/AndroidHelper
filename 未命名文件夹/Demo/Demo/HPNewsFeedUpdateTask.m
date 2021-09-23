//
//  HPNewsFeedUpdateTask.m
//  Demo
//
//  Created by 赵健 on 2021/9/22.
//

#import "HPNewsFeedUpdateTask.h"

@implementation HPNewsFeedUpdateTask
-(id)initWithTimeIinterval:(NSTimeInterval)interval target:(id)target selector:(SEL)selector{
    if(self = [super init]) {
        self.target = target;
        self.selector = selector;
//        self.timer =
    }
    return self;
}

-(void)fetchAndUpdate:(NSTimer *)timer {
    // 检索feed

}

//vm_size_t getUsedMemory() {
//    task_basic_info_data_t info;
//    mach_msg_type_number_t size = sizeof(info);
//    kern_return_t kerr = task_info(mach_task_self(), TASK_BASIC_INFO, (task_info_t)&info, &size);
//    if(kerr == KERN_SUCCESS) {
//        return  info.resident_size;
//    } else {
//        return 0;
//    }
//}

//vm_size_t getFreeMemory() {
//    mach_port_t host = mach_host_self();
//}

@end
