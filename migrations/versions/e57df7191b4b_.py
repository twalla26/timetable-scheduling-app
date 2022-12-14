"""empty message

Revision ID: e57df7191b4b
Revises: 41e9d1571b53
Create Date: 2022-12-08 15:18:02.693144

"""
from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision = 'e57df7191b4b'
down_revision = '41e9d1571b53'
branch_labels = None
depends_on = None


def upgrade():
    # ### commands auto generated by Alembic - please adjust! ###
    op.create_table('schedule',
    sa.Column('id', sa.Integer(), nullable=False),
    sa.Column('possible_day', sa.String(), nullable=False),
    sa.Column('ambiguous_day', sa.String(), nullable=True),
    sa.Column('impossible_day', sa.String(), nullable=True),
    sa.Column('create_date', sa.DateTime(), nullable=False),
    sa.Column('modify_date', sa.DateTime(), nullable=True),
    sa.Column('user_id', sa.Integer(), nullable=False),
    sa.Column('plan_id', sa.Integer(), nullable=False),
    sa.ForeignKeyConstraint(['plan_id'], ['plan.id'], name=op.f('fk_schedule_plan_id_plan'), ondelete='CASCADE'),
    sa.ForeignKeyConstraint(['user_id'], ['user.id'], name=op.f('fk_schedule_user_id_user'), ondelete='CASCADE'),
    sa.PrimaryKeyConstraint('id', name=op.f('pk_schedule'))
    )
    # ### end Alembic commands ###


def downgrade():
    # ### commands auto generated by Alembic - please adjust! ###
    op.drop_table('schedule')
    # ### end Alembic commands ###
