# Getting started with Mifos X
Instructions for getting started are located at https://mifosforge.jira.com/wiki/display/MIFOSX/Getting+started+-+Contributing+to+MifosX

# Pull Request Checklist
Before sending out a pull request, please ensure that the following pre-requisites are taken care of

1. An integration test is added/updated to test the new feature developed/bug-fix being made

1. The pull request has a Jira issue associated with it. Details for creating issues on Jira are located at https://mifosforge.jira.com/wiki/display/MIFOSX/Getting+started+-+Contributing+to+MifosX#Gettingstarted-ContributingtoMifosX-CreateIssues

1. The Relevant Jira number (Ex: MIFOSX-1454) is present as a part of the commit message. Refer http://comments.gmane.org/gmane.comp.finance.mifos.devel/14664 for details

1. Newly added code has been formatted and cleaned up as per our preferences at https://github.com/openMF/mifosx/wiki/Eclipse#apply-project-preferences-to-eclipse

1. Your work is present in a single commit rather than several broken up commits. This helps us maintain a cleaner commit history.  https://github.com/openMF/mifosx/wiki/Github-&-Git#contributing-back-changes-to-original-repositoryauthors has instructions for squashing multiple commits or resetting head and recommiting your work as a single commit

1. Your work is rebased on top of the latest code from develop branch to make life easier for our Committers

1. "gradlew licenseFormatMain licenseFormatTest licenseFormatIntegrationTest" has been run to apply the Mozilla License on all newly added files

