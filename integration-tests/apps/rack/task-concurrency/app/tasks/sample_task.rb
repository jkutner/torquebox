require 'torquebox-messaging'

class SampleTask < TorqueBox::Messaging::Task
  def foo(index)
    backchannel = TorqueBox::Messaging::Queue.new("/queues/backchannel")
    backchannel.publish Thread.current.object_id
  end
end
